import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../redux/store";
import { accountApi } from "../api/account";
import { editAccountList, editForeingAccountList } from "../redux/accountSlice";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination } from "swiper/modules";
import { IoIosArrowForward } from "react-icons/io";
import MainMeetingAccount from "../components/mainpage/MainMeetingAccount";
import "../css/swiper.css";
import "swiper/css/pagination";
import "swiper/css";
import ExchangeRate from '../components/exchange/ExchangeRate';
import { ExchangeRateInfo } from '../types/exchange';
import { userApi } from "../api/user";

const MainPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const userId = localStorage.getItem("userId");
  const userIdNumber = userId ? parseInt(userId, 10) : 0;
  const accountList = useSelector((state: RootState) => state.account.accountList);
  const foreignAccountList = useSelector((state: RootState) => state.account.foreignAccountList);
  const [exchangeRates, setExchangeRates] = useState<ExchangeRateInfo[]>([]);

  const navigateTransfermation = () => {
    navigate("/transaction")
  }

  const navigateAccountBook = () => {
    navigate("/accountbookdetail")
  }

  const navigateExchangeRate = () => {
    navigate("/exchangerate")
  }

  // 환율 받아오기
  const handleExchangeRatesUpdate = (rates: ExchangeRateInfo[]) => {
    setExchangeRates(rates);
  };

  const getExchangeRate = (currencyCode: string) => {
    const rate = exchangeRates.find(r => r.currencyCode === currencyCode);
    return rate ? rate.exchangeRate.toFixed(2) : 'N/A';
  };

  const getLatestUpdateTime = () => {
    if (exchangeRates.length === 0) return 'N/A';
    const latestDate = new Date(Math.max(...exchangeRates.map(r => new Date(r.created).getTime())));
    return latestDate.toLocaleString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    });
  };

  // 숫자를 세 자리마다 쉼표로 구분하여 표시
  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat("ko-KR").format(amount);
  };

  const formatAccountNumber = (accountNo: string) => {
    // 계좌번호를 각 4자리씩 나누고 '-'로 연결
    return accountNo.replace(/(\d{3})(\d{4})(\d{4})(\d{5})/, "$1-$2-$3-$4");
  };

  const subscribe = async () => {
    try {
      if (localStorage.getItem("userId") != null) {
        const sseUrl = `https://soltravel.shop/api/v1/notification/subscribe/${localStorage.getItem("userId")}`;
        console.log(sseUrl);

        const eventSource = new EventSource(sseUrl);

        eventSource.onmessage = (event) => {
          console.log(event.data);
        }

        eventSource.onopen = function (event) {
          console.log("SSE connection opened:", event);
        };

        eventSource.addEventListener("all", function(event){
          console.log("all: ",event.data);
        })

        eventSource.addEventListener("Exchange", function (event) {
          const data = JSON.parse(event.data);
          console.log("Exchange notification received:", data);
          // 알림 메시지를 화면에 표시하거나, 다른 UI 업데이트를 수행
        });

        eventSource.addEventListener("Settlement", function (event) {
          const data = JSON.parse(event.data);
          console.log("Settlement notification received:", data);
          // 알림 메시지를 화면에 표시하거나, 다른 UI 업데이트를 수행
        });

        eventSource.addEventListener("Transaction", function (event) {
          const data = JSON.parse(event.data);
          console.log("Transaction notification received:", data);
          // 알림 메시지를 화면에 표시하거나, 다른 UI 업데이트를 수행
        });

        eventSource.onerror = function (event) {
          console.error("Error occurred in SSE connection:", event);
          eventSource.close(); // 오류 발생 시 SSE 연결 닫기
        };

        eventSource.close = function() {
          console.log("SSE connection closed");
          // 재연결 로직을 추가할 수 있습니다.
        };
      }
    } catch (error) {
      console.error("Error creating user:", error);
      alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        // 두 API를 병렬로 호출
        const [accountResponse, foreignResponse] = await Promise.all([
          accountApi.fetchAccountInfo(userIdNumber),
          accountApi.fetchForeignAccountInfo(userIdNumber),
        ]);

        // Redux 스토어에 데이터 저장
        dispatch(editAccountList(accountResponse));
        dispatch(editForeingAccountList(foreignResponse));
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [dispatch]); // 의존성 배열에 필요한 값 추가

  return (
    <div className="w-full">
      <div className="w-full p-5 flex flex-col items-center space-y-4">
        {/* 모임통장 신청 */}
        <div className="w-full p-6 flex flex-col space-y-5 rounded-xl bg-white shadow-md">
          <div className="flex justify-between items-center">
            <div className="flex flex-col space-y-2">
              <p className="text-sm">모임통장과 외화통장을 한 번에</p>
              <div>
                <p className="text-lg font-bold">더 편한 환전</p>
                <p className="text-lg font-bold">해외여행 올인원 모임통장</p>
              </div>
            </div>

            <div>
              <img className="w-20" src="/assets/bankBookIcon.png" alt="올인원모임통장" />
            </div>
          </div>
          <button
            className="h-10 rounded-md bg-[#0046FF] font-bold text-white text-sm"
            onClick={() => {
              navigate("/meetingaccountcreateprepare");
            }}>
            신청하기
          </button>
        </div>

        {/* 입출금 통장 있을 시 표시 */}
        {accountList && accountList.length > 0 && (
          <div className="w-full py-5 px-5 flex flex-col rounded-xl bg-white shadow-md">
            <div 
            className="flex flex-col space-y-3"
            onClick={() => {
              navigate(`/accounthistory/${accountList[0].accountNo}`);
            }}>
              <div className="flex justify-between items-center">
                <div className="flex flex-col">
                  <p className="font-bold">올인원머니통장</p>
                  <p className="text-sm text-zinc-500">입출금 {formatAccountNumber(accountList[0].accountNo)}</p>
                </div>
              </div>
              <div className="flex items-center">
                <p className="text-[1.3rem] font-semibold">{formatCurrency(accountList[0].balance)}</p>
                <p className="text-[1rem]">원</p>
              </div>
              <hr />
            </div>
            <div className="flex justify-end mt-3">
              <button
              className="h-8 w-14 rounded-3xl bg-[#0046FF] font-bold text-white text-sm"
              onClick={navigateTransfermation}
              >
                이체
              </button>
            </div>
          </div>
        )}

        <div className="w-full flex flex-col items-center space-y-2">
          {/* 모임 통장 있을 시 표시 */}
          {accountList.length > 1 && (
            <Swiper
              pagination={{
                dynamicBullets: true,
              }}
              modules={[Pagination]}
              className="mainSwiper rounded-xl">
              {accountList.slice(1).map((account, index) => (
                <SwiperSlide>
                  <MainMeetingAccount index={index} account={account} foreignAccount={foreignAccountList[index]} />
                </SwiperSlide>
              ))}
            </Swiper>
          )}

          {/* 환율 표시 */}
          <div
          className="w-full p-6 flex flex-col space-y-2 rounded-xl bg-white shadow-md"
          onClick={navigateExchangeRate}
          >
            <div className="flex items-center space-x-1">
              <p className="text-md font-bold flex justify-start">환율</p>
              <IoIosArrowForward className="text-[#565656]" />
            </div>
            <div className="flex justify-end">
              <p className="text-sm text-zinc-400">매매기준율 {getLatestUpdateTime()} </p>
            </div>
            <div className="flex justify-between items-center">
              <div className="w-24 p-1 flex flex-col justify-center items-center space-y-2">
                <div className="flex justify-center items-center space-x-1">
                  <img className="w-6 h-5 rounded-sm" src="/assets/flag/flagOfTheUnitedStates.png" alt="미국" />
                  <p>USD</p>
                </div>
                <p className="text-lg font-semibold">{getExchangeRate('USD')}</p>
              </div>
              <div className="w-[0.8px] h-14 bg-gray-300"></div>
              <div className="w-24 p-1 flex flex-col justify-center items-center space-y-2">
                <div className="flex justify-center items-center space-x-1">
                  <img className="w-6 h-5 rounded-sm border" src="/assets/flag/flagOfJapan.png" alt="미국" />
                  <p>JPY</p>
                </div>
                <p className="text-lg font-semibold">{getExchangeRate('JPY')}</p>
              </div>
              <div className="w-[0.8px] h-14 bg-gray-300"></div>
              <div className="w-24 p-1 flex flex-col justify-center items-center space-y-2">
                <div className="flex justify-center items-center space-x-1">
                  <img className="w-6 h-5 rounded-sm" src="/assets/flag/flagOfEurope.png" alt="미국" />
                  <p>EUR</p>
                </div>
                <p className="text-lg font-semibold">{getExchangeRate('EUR')}</p>
              </div>
            </div>
            <button
              onClick={(e) => {
                navigate("/exchange");
                e.stopPropagation();
                navigate("/exchange")
              }}
              className="h-10 rounded-md bg-[#EAEAEA] font-bold text-sm">
              환전신청
            </button>
          </div>
        </div>

        {/* ExchangeRate 컴포넌트 (숨겨진 상태로 사용) */}
        <div style={{ display: 'none' }}>
          <ExchangeRate
            onCurrencyChange={() => {}}
            onExchangeRatesUpdate={handleExchangeRatesUpdate}
          />
        </div>

        {/* 가계부 */}
        <div
        className="w-full p-6 flex flex-col space-y-3 rounded-xl bg-blue-500 shadow-md"
        onClick={navigateAccountBook}
        >
          <div className="flex items-center space-x-1">
            <p className="text-md text-white font-bold flex justify-start">가계부</p>
            <IoIosArrowForward className="text-white" />
          </div>
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-8">
              <img className="w-12" src="/assets/budgetIcon.png" alt="가계부아이콘" />
              <div className="flex flex-col">
                <p className="text-zinc-200 font-semibold text-sm">이번 여행</p>
                <p className="text-zinc-200 font-semibold text-sm">가계부 확인하기</p>
              </div>
            </div>
            <IoIosArrowForward className="text-white" />
          </div>
        </div>

        {/* 환전, 카드 신청 */}
        <div className="w-full grid grid-cols-2 gap-5">
          <div
            onClick={() => {
              navigate("/exchange");
            }}
            className="w-full h-40 p-5 rounded-xl bg-white shadow-md flex flex-col justify-between items-start space-y-8">
            <img className="w-12" src="/assets/exchangeMoneyIcon.png" alt="환전아이콘" />
            <div>
              <p className="font-bold">수수료 없는</p>
              <p className="font-bold">환전하기</p>
            </div>
          </div>

          <div className="w-full h-40 p-5 rounded-xl bg-white shadow-md flex flex-col items-start space-y-8"
          onClick={subscribe}>
            <img className="w-12" src="/assets/creditCardIcon.png" alt="카드아이콘" />
            <div>
              <p className="font-bold">카드 신청하기</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
