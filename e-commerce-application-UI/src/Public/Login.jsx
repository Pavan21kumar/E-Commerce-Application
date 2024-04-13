import React, { useState } from "react";
import { Link } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState(null); // State for error message
  const handleEmailChange = (e) => {
    const enteredEmail = e.target.value;
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Regex pattern for email validation

    if (!enteredEmail.match(emailPattern)) {
      setErrorMessage("Please Enter A Valid Email Address.");
    } else {
      setErrorMessage("");
    }
    setEmail(enteredEmail);
  };
  const handlePasswordChange = (e) => {
    const enteredPassword = e.target.value;
    const passwordPattern =
      /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+])(?=.*[a-zA-Z]).{8,}$/;
    if (!enteredPassword.match(passwordPattern)) {
      setErrorMessage(
        "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long."
      );
    } else {
      setErrorMessage("");
    }

    setPassword(enteredPassword);
  };
  const handleSignup = () => {
    // Save form data to state
    const formData = { email, phoneNumber };
    setSubmittedData(formData);

    // Reset form fields
    setEmail("");
    setPassword("");
  };
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <div className="bg-white p-8 shadow-md max-w-xl w-full max-h-fit hover:shadow-2xl">
        <h2 className="text-3xl font-bold mb-6 text-center ">Login Form</h2>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={handleEmailChange}
          className="border border-gray-400 rounded-md px-3 py-2 mb-4 w-full focus:outline-none focus:border-blue-500"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={handlePasswordChange}
          className="border border-gray-400 rounded-md px-3 py-2 mb-4 w-full focus:outline-none focus:border-blue-500"
        />
        {errorMessage && (
          <div className="text-red-500 text-xs">{errorMessage}</div>
        )}
        <p className="text-gray-600 mb-4 text-sm">
          By continuing, you agree to Flipkart's{" "}
          <span className="text-blue-500">Terms of Use</span> and{" "}
          <span className="text-blue-500">Privacy Policy.</span>
        </p>
        <button
          onClick={handleSignup}
          className="bg-orange-600 hover:bg-orange-700 text-white rounded-md px-4 py-2 w-full font-bold"
        >
          Submit
        </button>
        <p className="text-gray-600 mt-4">
          <span className="text-blue-500 font-bold">
            <Link to="/register">
              New to Flipkart? &nbsp; Create an account
            </Link>
          </span>
        </p>
        <Link to="/VerifyOTP">Verify Otp</Link>
      </div>
    </div>
  );
};
export default Login;
