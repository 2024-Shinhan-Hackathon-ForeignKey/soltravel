import React, { useEffect, useState } from "react";
import { RiHome5Line } from "react-icons/ri";
import { useSelector, useDispatch } from "react-redux";
import { setIsKeyboard, setAccountPassword } from "../../redux/accountSlice";
import { RootState } from "../../redux/store";
import SecurityKeyboard from "../../components/account/SecurityKeyboard";
import { useNavigate } from "react-router";
import NameInput from "../../components/account/inputField/NameInput";
import ResidentNumberInput from "../../components/account/inputField/ResidentNumberInput";
import PasswordInput from "../../components/account/inputField/PasswordInput";

const MeetingAccountCreate = () => {
  const { isKeyboard, accountPassword } = useSelector((state: RootState) => state.account);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [step, setStep] = useState(0);
  const stepList = ["모임 이름을", "어떤 모임인지를", "모임주 이름을", "모임주 주민등록번호를", "계좌 비밀번호를"];
  const [meetingName, setMeetingName] = useState("");
  const [meetingType, setMeetingType] = useState("");
  const [name, setName] = useState("");
  const [residentNumber, setResidentNumber] = useState("");
  const [maskedPassword, setMaskedPassword] = useState("");

  useEffect(() => {
    // redux store의 기존 저장되어있던 정보 제거
    dispatch(setAccountPassword(""));
  }, []);

  useEffect(() => {
    if (accountPassword !== undefined) {
      setMaskedPassword("●".repeat(accountPassword.length));
    }
  }, [accountPassword]);

  const handleMeetingNameChange = (meetingname: string) => {
    setMeetingName(meetingname);
    if (meetingname.length >= 2) {
      setStep(1);
    }
  };

  const handleMeetingTypeChange = (meetingType: string) => {
    setMeetingType(meetingType);
    if (meetingType.length >= 2) {
      setStep(2);
    }
  };

  const handleNameChange = (name: string) => {
    setName(name);
    if (name.length >= 3) {
      setStep(3);
    }
  };

  const handleResidentNumberChange = (number: string) => {
    if (14 - number.length <= 6) {
      let temp = number.substring(0, 8);
      temp += "*".repeat(number.length - 8);
      number = temp;
    }

    let masking = number.replace(/^(\d{0,6})(\d{0,7})$/g, "$1-$2").replace(/\-{1}$/g, "");
    setResidentNumber(masking);

    if (number.length === 14) {
      // 하이픈 포함
      setStep(4);
      dispatch(setIsKeyboard(true));
    }
  };

  const handlePasswordKeyboard = () => {
    dispatch(setAccountPassword(""));
    dispatch(setIsKeyboard(true));
  };

  return (
    <div className="h-full flex flex-col justify-between">
      <div className="flex flex-col space-y-5">
        <div className="p-5 grid grid-cols-[1fr_8fr_1fr]">
          <div className="flex items-center">
            <RiHome5Line className="text-2xl" />
          </div>
          <p className="text-xl text-center font-semibold">일반모임통장 가입정보</p>
        </div>

        <div className="p-5 grid gap-8">
          <div className="text-2xl font-semibold">
            <p>{stepList[step]}</p>
            <p>입력해주세요</p>
          </div>

          <div className="grid gap-3">
            <div
              className={`transition-transform duration-300 ease-in-out ${
                step > 3 ? "translate-y-[3px]" : "translate-y-0"
              }`}>
              {step > 3 && <PasswordInput maskedPassword={maskedPassword} onKeyboardOpen={handlePasswordKeyboard} />}
            </div>

            <div
              className={`transition-transform duration-300 ease-in-out ${
                step > 2 ? "translate-y-[3px]" : "translate-y-0"
              }`}>
              {step > 2 && (
                <ResidentNumberInput residentNumber={residentNumber} onChange={handleResidentNumberChange} />
              )}
            </div>

            <div
              className={`transition-transform duration-300 ease-in-out ${
                step > 1 ? "translate-y-0" : "translate-y-[3px]"
              }`}>
              {step > 1 && <NameInput labelName="모임주" name={name} onChange={handleNameChange} />}
            </div>

            <div
              className={`transition-transform duration-300 ease-in-out ${
                step > 0 ? "translate-y-0" : "translate-y-[3px]"
              }`}>
              {step > 0 && <NameInput labelName="모임종류" name={meetingType} onChange={handleMeetingTypeChange} />}
            </div>

            <div
              className={`transition-transform duration-300 ease-in-out ${
                step === 0 ? "translate-y-0" : "translate-y-[3px]"
              }`}>
              <NameInput labelName="모임명" name={meetingName} onChange={handleMeetingNameChange} />
            </div>
          </div>
        </div>
      </div>

      <div className="px-5 py-10">
        <button
          className={`w-full py-3 text-white bg-[#0471E9] rounded-lg ${
            step !== 4 ||
            meetingName.length < 1 ||
            meetingType === "" ||
            name.length < 2 ||
            residentNumber.length !== 14 ||
            maskedPassword.length !== 4
              ? "opacity-40"
              : ""
          }`}
          onClick={() => navigate("")}
          disabled={
            step !== 4 ||
            meetingName.length < 1 ||
            meetingType === "" ||
            name.length < 2 ||
            residentNumber.length !== 14 ||
            maskedPassword.length !== 4
          }>
          완료
        </button>
      </div>

      {isKeyboard ? (
        <div className="w-full fixed bottom-0">
          <SecurityKeyboard />
        </div>
      ) : (
        <></>
      )}
    </div>
  );
};

export default MeetingAccountCreate;
