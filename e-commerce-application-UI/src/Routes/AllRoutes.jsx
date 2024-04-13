import React from "react";
import App from "../App";
import { Route, Routes } from "react-router-dom";
import Home from "../Public/Home";
import Explore from "../Public/Explore";
import VerifyOTP from "../Public/VerifyOTP";
import BecomeASeller from "../Private/Seller/BecomeASeller";
import SellerDashboard from "../Private/Seller/SellerDashboard";
import Cart from "../Private/Customer/Cart";
import WishList from "../Private/Customer/WishList";
import Orders from "../Private/Customer/Orders";
import Account from "../Private/Common/Account";
import EditProfile from "../Private/Common/EditProfile";
import Register from '../Public/Register'
import Header from "../Util/Header";
import Login from "../Public/Login";
<Route path="/" element={<App />}></Route>;
const AllRoutes = () => {
  const user = {
    userId: "123",
    userName: "Simanta Sen",
    role: "CUSTOMER",
    authenticated: false,
    accessExpiration: 3600,
    refreshExpiration: 1296000,
  };
  const { role, authenticated } = user;
  let routes = [];
  if (authenticated) {
    role === "SELLER"
      ? routes.push(
      <Route key={"sellerDashBoard"} path="/sellerDashBoard" element={<SellerDashboard />}/>)
      : role === "CUSTOMER" &&
        routes.push(
          <Route key={"cart"} path="/cart" element={<Cart />} />,
          <Route key={"wishList"} path="/wishList" element={<WishList />}/>,
          <Route key={"orders"} path="/orders" element={<Orders />} />
        );
    routes.push(
      <Route key={"editProfile"} path="editProfile" element={<EditProfile />}/>,
      <Route key={"account"} path="account" element={<Account />} />
    );
  } 
  else {
    role === "CUSTOMER" &&
      routes.push(
        <Route key={Home} path="/home" element={<Home />} />,
        <Route key={Explore} path="/explore" element={<Explore />} />,
        <Route key={Register} path="/Register" element={<Register/>} />,
        <Route key={VerifyOTP} path="/VerifyOTP" element={<VerifyOTP />} />,
        <Route key={Login} path="/Login" element={<Login/>} />,
        <Route key={Header} path="/Header" element={<Header />} />,
        <Route key={SellerDashboard} path="/SellerDashboard" element={<SellerDashboard />}/>)
  }
  routes.map((route) => console.log(route.props));
  return (
    <Routes>
      <Route key={"/"} path="/" element={<App />}>{routes}</Route>
    </Routes>
  );
};
export default AllRoutes;
