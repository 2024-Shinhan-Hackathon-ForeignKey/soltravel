import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router";
import { accountApi } from "../../api/account";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import { Swiper, SwiperSlide } from "swiper/react";
import { FreeMode, Pagination } from "swiper/modules";
import "swiper/css";
import "swiper/css/free-mode";
import "swiper/css/pagination";
import { PiAirplaneTiltFill } from "react-icons/pi";
import { RiHome5Line } from "react-icons/ri";
import AccountDetail from "./AccountDetail";
import { AccountInfo } from "../../types/account";

const JoinedMeetingAccountDetail = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [numberIndex, setNumberIndex] = useState(Number(id) + 1);

  const accountList = useSelector((state: RootState) => state.account.accountList);
  const foreignAccountList = useSelector((state: RootState) => state.account.foreignAccountList);
  const [selectedAccount, setSelectedAccount] = useState<AccountInfo | null>(null);
  const [selectedForeignAccount, setSelectedForeignAccount] = useState<AccountInfo | null>(null);
  const [memberList, setMemberList] = useState<string[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    if (accountList.length > 0 && !isNaN(numberIndex)) {
      if (numberIndex >= 0 && numberIndex < accountList.length) {
        setSelectedAccount(accountList[numberIndex]);
        setSelectedForeignAccount(foreignAccountList[numberIndex - 1]);
      } else {
        setSelectedAccount(null);
        setSelectedForeignAccount(null);
      }
    }
  }, [accountList, numberIndex]);

  useEffect(() => {
    const getParticipants = async () => {
      try {
        if (selectedAccount === null) return;

        setLoading(true); // 데이터 로딩 시작
        const response = await accountApi.fetchParticipantInfo(selectedAccount.id);
        const participantNames = response.participants.map((participant: any) => participant.userInfo.username);
        setMemberList(participantNames);
      } catch (error) {
        console.error("Error fetching data:", error);
        alert("참여자 정보 조회에 실패했습니다.");
      } finally {
        setLoading(false); // 데이터 로딩 완료
      }
    };

    getParticipants();
  }, [selectedAccount]);

  if (selectedAccount === null) {
    return <p>계좌 정보를 불러오는 중입니다...</p>;
  }

  return (
    <>
      {accountList.length > 0 && foreignAccountList.length > 0 && (
        <div className="w-full h-full pb-16 bg-[#EFEFF5]">
          <div className="p-5 flex flex-col bg-[#c3d8eb]">
            <div className="mb-12 flex space-x-2 items-center justify-start">
              <RiHome5Line
                onClick={() => {
                  navigate("/");
                }}
                className="text-2xl text-zinc-600 cursor-pointer"
              />
              <p className="text-sm font-bold flex items-center">{selectedAccount.groupName}</p>
            </div>

            <div className="w-full flex flex-col items-center space-y-5">
              <div className="w-full p-2 flex justify-center space-x-2">
                {loading ? (
                  <p>로딩 중...</p> // 로딩 중일 때 표시할 내용
                ) : (
                  <Swiper
                    slidesPerView={3.7}
                    spaceBetween={20}
                    freeMode={true}
                    modules={[FreeMode, Pagination]}
                    className="userSwiper">
                    {memberList.map((name, index) => (
                      <SwiperSlide key={index}>
                        <div className="flex flex-col justify-center items-center space-y-1">
                          <img className="w-12" src="/assets/user/userIconSample.png" alt={name} />
                          <p className="text-xs text-zinc-800">{name}</p>
                        </div>
                      </SwiperSlide>
                    ))}
                  </Swiper>
                )}
              </div>
            </div>
          </div>
          <div className="w-full p-5 flex flex-col">
            <div className="mb-3 flex items-center space-x-[9px]">
              <div className="w-6 h-6 bg-[#638ee4] rounded-full flex justify-center items-center">
                <PiAirplaneTiltFill className="text-zinc-50" />
              </div>
              <p className="text-sm text-zinc-800 font-bold">직장</p>
            </div>
            <hr className="mb-3 border-0 border-t-[0.5px] border-zinc-200" />
            <AccountDetail account={selectedAccount} foreignAccount={selectedForeignAccount} />
          </div>
        </div>
      )}
    </>
  );
};

export default JoinedMeetingAccountDetail;
