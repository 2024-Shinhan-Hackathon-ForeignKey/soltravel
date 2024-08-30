import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router';
import { accountApi } from '../../api/account';
import { accountHistoryApi } from '../../api/accountHistory';
import { AccountInfo } from '../../types/account';
import { AccountHistoryRequest, AccountHistoryResponse } from '../../types/accountHistory';

const AccountHistory: React.FC = () => {
  const { accountNo } = useParams<{ accountNo: string }>();
  const navigate = useNavigate();
  const [accountInfo, setAccountInfo] = useState<AccountInfo | null>(null);
  const [transactions, setTransactions] = useState<AccountHistoryResponse[]>([]);
  const [showBalance, setShowBalance] = useState(true);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      if (!accountNo) {
        setError('계좌 번호가 없습니다.');
        navigate('/')
        return;
      }

      try {
        const userId = localStorage.getItem('userId');
        if (!userId) {
          setError('로그인이 필요합니다.');
          navigate('/login')
          return;
        }

        const accounts = await accountApi.fetchAccountInfo(parseInt(userId));
        const account = accounts.find(acc => acc.accountNo === accountNo);
        if (!account) {
          setError('계좌를 찾을 수 없습니다.');
          setIsLoading(false);
          return;
        }

        setAccountInfo(account);

        const today = new Date();
        const oneMonthAgo = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());

        // 요청에 맞게 날짜 파일 고치기
        const formatDate = (date: Date): string => {
          const year = date.getFullYear();
          const month = (date.getMonth() + 1).toString().padStart(2, '0');
          const day = date.getDate().toString().padStart(2, '0');
          return `${year}${month}${day}`
        }

        const historyRequest: AccountHistoryRequest = {
          startDate: formatDate(oneMonthAgo),
          endDate: formatDate(today),
          transactionType: 'A',
          orderByType: 'ASC'
        };

        const history = account.accountType === 'FOREIGN'
          ? await accountHistoryApi.ForeignAccountHistoryInfo(accountNo, historyRequest)
          : await accountHistoryApi.AccountHistoryInfo(accountNo, historyRequest);

        console.log(history)
        setTransactions(history);
        console.log(history)
      } catch (error) {
        console.error('Error fetching data:', error);
        setError('데이터를 불러오는 데 실패했습니다.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [accountNo, navigate]);

  if (isLoading) return <div>Loading...</div>
  if (error) return <div>{error}</div>
  if (!accountInfo) return <div>계좌 정보를 찾을 수 없습니다.</div>

  const getCurrencyDisplay = (currencyCode: string) => {
    return currencyCode === 'KRW' ? '원' : currencyCode;
  };

  // 날짜 별로 묶어주기
  const groupedTransactions = transactions.length > 0
    ? transactions.reduce((groups, transaction) => {
        const date = transaction.transactionDate;
        if (!groups[date]) {
          groups[date] = [];
        }
        groups[date].push(transaction);
        return groups;
      }, {} as Record<string, AccountHistoryResponse[]>)
    : {};

  // 날짜 점으로 나눠주기
  const formatDate = (dateString: string) => {
    const year = dateString.slice(0, 4);
    const month = dateString.slice(4, 6);
    const day = dateString.slice(6, 8);
    return `${year}.${month}.${day}`;
  };

  // 시간 콜론으로 나누기
  const formatTime = (timeString: string) => {
    const hours = timeString.slice(0, 2);
    const minutes = timeString.slice(2, 4);
    const seconds = timeString.slice(4, 6);
    return `${hours}:${minutes}:${seconds}`;
  };

  return (
    <div className='p-4 max-w-md mx-auto bg-gray-100 min-h-screen'>
      {/* 계좌 정보 섹션 */}
      <div className='p-4 mb-4 bg-white rounded-lg shadow'>
        <div className='mb-2 flex items-center'>
          <div className='w-8 h-8 mr-2 bg-[#0046FF] rounded-full'></div>
          <div className='ml-3'>
            <p className='font-semibold'>{accountInfo.accountName}</p>
            <p className='text-sm text-gray-500'>{accountInfo.accountNo}</p>
          </div>
        </div>
        <p className='text-2xl font-bold text-right'>
          {accountInfo.balance.toLocaleString()} {getCurrencyDisplay(accountInfo.currency.currencyCode)}
          </p>
      </div>

      {/* 버튼 섹션 */}
      <div className="mb-4 flex space-x-2">
        <button className='py-2 flex-1 bg-[#0046FF] text-white rounded' onClick={() => navigate(`/transfer/${accountInfo.accountNo}`)}>이체</button>
        <button className='py-2 flex-1 bg-[#0046FF] text-white rounded' onClick={() => navigate(`/account-management/${accountInfo.accountNo}`)}>계좌 관리</button>
      </div>

      {/* 거래 내역 섹션 */}
      <div className='bg-white rounded-lg p-4'>
        <div className='flex items-center justify-between mb-4'>
          <span className='text-lg font-semibold'>최신순</span>
          <div className='flex items-center'>
            <span className='mr-2'>잔액보기</span>
            <button
              className={`w-12 h-6 rounded-full ${showBalance ? 'bg-blue-500' : 'bg-gray-300'}`}
              onClick={() => setShowBalance(!showBalance)}
            >
              <div className={`w-5 h-5 rounded-full bg-white transform transition-transform ${showBalance ? 'translate-x-6' : 'translate-x-1'}`} />
            </button>
          </div>
        </div>

        {Object.entries(groupedTransactions).length > 0 ? (
          Object.entries(groupedTransactions).map(([date, dayTransactions], groupIndex, groupArray) => (
            <div key={date} className={`mb-6 ${groupIndex !== groupArray.length - 1 ? 'border-b border-gray-200 pb-6' : ''}`}>
              <h3 className='text-lg font-semibold mb-4'>{formatDate(date)}</h3>
              {dayTransactions.map((transaction, index) => (
                <div key={index} className='mb-4 last:mb-0'>
                  <div className='flex justify-between items-start'>
                    <div>
                      <p className='font-semibold'>{formatTime(transaction.transactionTime)}</p>
                      <p className='text-gray-600'>{transaction.transactionSummary || transaction.transactionTypeName}</p>
                    </div>
                    <div className='text-right'>
                      <p className={`font-semibold ${transaction.transactionType === '2' ? 'text-[#0046FF]' : 'text-red-500'}`}>
                        <div>{transaction.transactionType === '2' ? '출금' : '입금'}</div>
                        {parseFloat(transaction.transactionBalance).toLocaleString()} {getCurrencyDisplay(accountInfo.currency.currencyCode)}
                      </p>
                      {showBalance && (
                        <p className='text-sm text-gray-500'>
                          잔액 {parseFloat(transaction.transactionAfterBalance).toLocaleString()} {getCurrencyDisplay(accountInfo.currency.currencyCode)}
                        </p>
                      )}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ))
        ) : (
          <div className="text-center text-gray-500">거래 내역이 없습니다.</div>
        )}
      </div>
    </div>
  );
};

export default AccountHistory;