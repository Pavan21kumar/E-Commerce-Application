import React, { useState } from "react";
import { Link } from "react-router-dom";
const VerifyOTP = () => {
  const [otp, setOTP] = useState("");
  const [errorMessage, setErrorMessage] = useState(null); // State for error message
  const handleOTP = (e) => {
    const enteredOTP = e.target.value;
    const otpPattern = /^[0-9]{6}$/; // Regex pattern for email validation

    if (!enteredOTP.match(otpPattern)) {
      setErrorMessage("Please Enter A Valid OTP Of 6 Digit");
    } 
    else {
      setErrorMessage("");
    }
    setOTP(enteredOTP);
  };
  const handleSignup = () => {
    // Save form data to state
    const formData = {otp};
    setSubmittedData(formData);
 // Reset form fields
    setOTP("");
  };
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <div className="bg-white p-8 shadow-md max-w-xl w-full max-h-fit hover:shadow-2xl">
        <h2 className="text-3xl font-bold mb-6 text-center ">Verify OTP</h2>
        <input
          type="text"
          placeholder="Enter 6-Digit OTP"
          value={otp}
          onChange={handleOTP}
          className="border border-gray-400 rounded-md px-3 py-2 mb-4 w-full focus:outline-none focus:border-blue-500"
        />
        {errorMessage && (
          <div className="text-red-500 text-xs">{errorMessage}</div>
        )}
        <button
          onClick={handleSignup}
          className="bg-orange-600 hover:bg-orange-700 text-white rounded-md px-4 py-2 w-full font-bold"
        >
          Submit
        </button>
      </div>
    </div>
  );
};
export default VerifyOTP;
