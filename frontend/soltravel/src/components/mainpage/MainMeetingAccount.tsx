import React from "react";
import { useNavigate } from "react-router";
import { IoSchool } from "react-icons/io5";
import { PiAirplaneTiltFill } from "react-icons/pi";
import path from "path";

interface Props {
  account: {
    MeetingAccountName: string;
    MeetingAccountIcon: string;
    normalMeetingAccount: {
      accountNumber: string;
      accountMoney: string;
    };
    foreignMeetingAccount: {
      accountNumber: string;
      accountMoney: string;
      currencyType: string;
    };
  };
}

const MainMeetingAccount = ({ account }: Props) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => {
        navigate("/account");
      }}
      className="w-full py-5 pb-10 px-5 flex flex-col rounded-xl bg-white shadow-md">
      <div className="flex flex-col space-y-4">
        <div className="flex items-center space-x-[9px] mb-1">
          {account.MeetingAccountIcon === "airplane" ? (
            <div className="w-6 h-6 bg-[#638ee4] rounded-full flex justify-center items-center">
              <PiAirplaneTiltFill className="text-zinc-50" />
            </div>
          ) : (
            <div className="w-6 h-6 bg-[#61bb9d] rounded-full flex justify-center items-center">
              <IoSchool className="text-zinc-50" />
            </div>
          )}
          <p className="font-bold">{account.MeetingAccountName}</p>
        </div>
        <div className="rounded-md flex justify-between">
          <div>
            <p className="text-sm font-bold">올인원 일반모임통장</p>
            <p className="text-sm text-zinc-500">{account.normalMeetingAccount.accountNumber}</p>
          </div>
          <div className="flex items-center">
            <p className="text-[1.3rem] font-semibold">{account.normalMeetingAccount.accountMoney}</p>
            <p className="text-[1rem]">원</p>
          </div>
        </div>
        <hr />
        <div
          onClick={(e) => {
            e.stopPropagation();
            navigate("/foreignaccount");
          }}
          className="rounded-md flex justify-between">
          <div className="flex flex-col">
            <p className="text-sm font-bold">올인원 외화모임통장</p>
            <p className="text-sm text-zinc-500">{account.foreignMeetingAccount.accountNumber}</p>
          </div>

          <div className="flex items-center">
            <p className="text-[1.3rem] font-semibold">{account.foreignMeetingAccount.accountMoney}</p>
            <p className="text-[1rem]">{account.foreignMeetingAccount.currencyType}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainMeetingAccount;
