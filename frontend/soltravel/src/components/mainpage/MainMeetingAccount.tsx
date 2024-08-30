import React from "react";
import { useNavigate } from "react-router";
import { IoSchool, IoHome } from "react-icons/io5";
import { PiAirplaneTiltFill } from "react-icons/pi";
import { FaCar, FaUserFriends, FaBriefcase, FaHeart } from "react-icons/fa"; // 필요한 추가 아이콘 임포트
import path from "path";
import { AccountInfo } from "../../types/account";

interface Props {
  index: number;
  account: AccountInfo;
  foreignAccount: AccountInfo;
}

const MainMeetingAccount = ({ index, account, foreignAccount }: Props) => {
  const navigate = useNavigate();

  // 숫자를 세 자리마다 쉼표로 구분하여 표시
  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat("ko-KR").format(amount);
  };

  const formatAccountNumber = (accountNo: string) => {
    // 계좌번호를 각 4자리씩 나누고 '-'로 연결
    return accountNo.replace(/(\d{3})(\d{4})(\d{4})(\d{5})/, "$1-$2-$3-$4");
  };
  
  const getIcon = (iconName: string) => {
    const containerClasses = "w-6 h-6 bg-[#638ee4] rounded-full flex justify-center items-center text-white";
    const iconClasses = "w-4 h-4"; // 아이콘 자체 크기를 줄이기 위한 클래스
  
    let IconComponent;
  
    switch (iconName) {
      case "airPlane":
        IconComponent = <PiAirplaneTiltFill className={iconClasses} />;
        break;
      case "friend":
        IconComponent = <FaUserFriends className={iconClasses} />;
        break;
      case "family":
        IconComponent = <IoHome className={iconClasses} />;
        break;
      case "lover":
        IconComponent = <FaHeart className={iconClasses} />;
        break;
      case "job":
        IconComponent = <FaBriefcase className={iconClasses} />;
        break;
      default:
        IconComponent = <PiAirplaneTiltFill className={iconClasses} />;
        break;
    }
  
    return (
      <span className={containerClasses}>
        {IconComponent}
      </span>
    );
  };

  return (
    <>
      {account && (
        <div
          onClick={() => {
            navigate(`/meetingaccount/${index}`);
          }}
          className="w-full py-5 pb-10 px-5 flex flex-col rounded-xl bg-white shadow-md">
          <div className="flex flex-col space-y-4">
            <div className="flex items-center space-x-1 mb-1">
              {getIcon(account.iconName)}
              <p className="font-bold">{account.groupName}</p>
            </div>
            <div className="rounded-md flex justify-between">
              <div>
                <p className="text-sm font-bold">올인원 일반모임통장</p>
                <p className="text-sm text-zinc-500">{formatAccountNumber(account.accountNo)}</p>
              </div>
              <div className="flex items-center space-x-1">
                <p className="text-[1.3rem] font-semibold">{formatCurrency(account.balance)}</p>
                <p className="text-[1rem]">원</p>
              </div>
            </div>
            <hr />
            {foreignAccount && (
              <div
                onClick={(e) => {
                  e.stopPropagation();
                  navigate("/foreignaccount");
                }}
                className="rounded-md flex justify-between">
                <div className="flex flex-col">
                  <p className="text-sm font-bold">올인원 외화모임통장</p>
                  <p className="text-sm text-zinc-500">{formatAccountNumber(foreignAccount.accountNo)}</p>
                </div>

                <div className="flex items-center space-x-1">
                  <p className="text-[1.3rem] font-semibold">{formatCurrency(foreignAccount.balance)}</p>
                  <p className="text-[1rem]">{foreignAccount.currency.currencyCode}</p>
                </div>
              </div>
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default MainMeetingAccount;
