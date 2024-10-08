import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import { exchangeApi } from "../../api/exchange";
import { accountApi } from "../../api/account";
import { ExchangeRateInfo as ExchangeRateInfoType } from "../../types/exchange";
import { useNavigate } from "react-router";
import { RiHome5Line } from "react-icons/ri";
import TypeSelect from "../../components/account/inputField/TypeSelect";
import { currencyTypeList } from "../../types/exchange";
import { MeetingAccountCreate } from "../../types/account";
import ExchangeRateInput from "../../components/account/inputField/ExchangeRateInput";
import TravleDateRangePicker from "../../components/account/inputField/TravelDateRangePicker";
import { Dayjs } from "dayjs";

const ForeignMeetingAccountCreate = () => {
  const generalMeetingAccountDetail = useSelector((state: RootState) => state.account.generalMeetingAccountDetail);
  const navigate = useNavigate();

  const [step, setStep] = useState(0);
  const stepList = ["통화를", "희망 환율을", "여행 일정을"];
  const [currencyType, setCurrencyType] = useState("");
  const [exchangeRate, setExchangeRate] = useState(0);
  const [exchangeRateBDP, setExchangeRateBDP] = useState(0);
  const [currentExchangeRate, setCurrentExchangeRate] = useState(0);
  const [travelSchedule, setTravelSchedule] = useState<{ startDate: Dayjs | null; endDate: Dayjs | null }>({
    startDate: null,
    endDate: null,
  });
  const [currencies, setCurrencies] = useState<ExchangeRateInfoType[]>([]);

  useEffect(() => {
    fetchExchangeRates();
  }, []);

  useEffect(() => {
    if (currencyType && currencies.length > 0) {
      const selectedCurrency = currencies.find((currency) => currency.currencyCode === currencyType);
      if (selectedCurrency) {
        setCurrentExchangeRate(selectedCurrency.exchangeRate);
      } else {
        setCurrentExchangeRate(0);
      }
    }
  }, [currencyType, currencies]);

  const fetchExchangeRates = async () => {
    try {
      const data = await exchangeApi.getExchangeRates();
      setCurrencies(data);
    } catch (error) {
      console.error("환율 정보를 가져오는 데 실패했습니다. 다시 시도해 주세요.", error);
    }
  };

  const handleCurrencyTypeChange = (currencyType: string) => {
    setCurrencyType(currencyType);
    if (currencyType !== "") {
      setStep(1);
      console.log(step);
    }
  };

  const handleExchangeRateChange = (exchangeRate: number) => {
    setExchangeRate(exchangeRate);
    if (exchangeRate > 0) {
      setStep(2);
    }
  };

  const handleExchangeRateChangeBDP = (exchangeRateBDP: number) => {
    setExchangeRateBDP(exchangeRateBDP);
    if (exchangeRateBDP > 0) {
      setStep(2);
    }
  };

  const handleTravelStartDate = (startDate: Dayjs) => {
    setTravelSchedule((prevSchedule) => ({
      ...prevSchedule,
      startDate,
    }));
  };

  const handleTravelEndDate = (endDate: Dayjs) => {
    setTravelSchedule((prevSchedule) => ({
      ...prevSchedule,
      endDate,
    }));
  };

  const handleNext = () => {
    if (!travelSchedule.startDate || !travelSchedule.endDate) {
      alert("여행 일정의 시작일과 종료일을 모두 입력해주세요.");
      return;
    }

    if (currencyType !== "USD") {
      alert("준비 중인 통화입니다. 현재는 미국 달러만 지원합니다.");
    }

    const combinedExchangeRate = parseFloat(`${exchangeRate}.${exchangeRateBDP}`);

    const accountRequest: MeetingAccountCreate = {
      accountType: "GROUP",
      accountPassword: generalMeetingAccountDetail.generalMeetingAccountPassword,
      groupName: generalMeetingAccountDetail.generalMeetingAccountName,
      travelStartDate: travelSchedule.startDate.format("YYYY-MM-DD"),
      travelEndDate: travelSchedule.endDate.format("YYYY-MM-DD"),
      currencyCode: currencyType,
      iconName: generalMeetingAccountDetail.generalMeetingAccountIcon,
      exchangeRate: combinedExchangeRate,
      participantInfos: generalMeetingAccountDetail.generalMeetingAccountMemberList,
    };

    console.log(accountRequest);
    try {
      const response = accountApi.fetchCreateMeetingAccount(generalMeetingAccountDetail.generalMeetingAccountMemberList[0].userId, accountRequest);
      navigate("/meetingaccountcreatecomplete");
    } catch (error) {
      console.error("모임통장 생성에 실패했습니다. 다시 시도해 주세요.", error);
    }
  };

  return (
    <div className="h-full flex flex-col justify-between">
      <div className="flex flex-col space-y-5">
        <div className="p-5 grid grid-cols-[1fr_8fr_1fr]">
          <div className="flex items-center">
            <RiHome5Line
              onClick={() => {
                navigate("/");
              }}
              className="text-2xl"
            />
          </div>
          <p className="text-xl text-center font-semibold">외화모임통장 가입정보</p>
        </div>

        <div className="p-5 grid gap-8">
          <div className="grid gap-3">
            <div className="flex space-x-2">
              <p className="text-[#0471E9] font-semibold">02</p>
              <p className="font-semibold">외화모임통장 계좌개설</p>
            </div>

            <div className="text-2xl font-semibold">
              <p>{stepList[step]}</p>
              <p>입력해주세요</p>
            </div>
          </div>

          <div className="grid gap-3">
            <div
              className={`transition-transform duration-300 ease-in-out ${
                step > 1 ? "translate-y-0" : "translate-y-[3px]"
              }`}>
              {step > 1 && (
                <TravleDateRangePicker
                  schedule={travelSchedule}
                  onStartChange={handleTravelStartDate}
                  onEndChange={handleTravelEndDate}
                />
              )}
            </div>

            <div
              className={`transition-transform duration-300 ease-in-out ${
                step > 0 ? "translate-y-0" : "translate-y-[3px]"
              }`}>
              {step > 0 && (
                <ExchangeRateInput
                  currencyType={currencyType}
                  exchangeRate={exchangeRate}
                  exchangeRateBDP={exchangeRateBDP}
                  currentExchangeRate={currentExchangeRate}
                  onChange={handleExchangeRateChange}
                  onChangeBDP={handleExchangeRateChangeBDP}
                />
              )}
            </div>

            <div
              className={`transition-transform duration-300 ease-in-out ${
                step === 0 ? "translate-y-0" : "translate-y-[3px]"
              }`}>
              <TypeSelect
                label="통화"
                menuList={currencyTypeList}
                selectedType={currencyType}
                onChange={handleCurrencyTypeChange}
              />
            </div>
          </div>
        </div>
      </div>

      <div className="px-5 py-10">
        <button
          className={`w-full py-3 text-white bg-[#0471E9] rounded-lg ${
            step !== 2 ||
            currencyType === "" ||
            exchangeRate === 0 ||
            travelSchedule.startDate === null ||
            travelSchedule.endDate === null
              ? "opacity-40"
              : ""
          }`}
          onClick={() => handleNext()}
          disabled={
            step !== 2 ||
            currencyType === "" ||
            exchangeRate === 0 ||
            travelSchedule.startDate === null ||
            travelSchedule.endDate === null
          }>
          완료
        </button>
      </div>
    </div>
  );
};

export default ForeignMeetingAccountCreate;
