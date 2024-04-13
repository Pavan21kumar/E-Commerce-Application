import React, { useState } from "react";
import { Link } from "react-router-dom";
import image1 from "../images/A1.jpg";
import SearchBar from "../Components/SearchBar";
import { FaBoxOpen, FaRegPlusSquare, FaRegUserCircle } from "react-icons/fa";
import im5 from "../images/D5.jpg";
import im6 from "../images/D6.jpg";
import { IoBagHandle, IoCartOutline } from "react-icons/io5";
import { FaRegCircleUser } from "react-icons/fa6";
import { FiBox } from "react-icons/fi";
import { BsGift } from "react-icons/bs";
import { MdLogin } from "react-icons/md";
import { FaBox } from "react-icons/fa";
const Header = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const handleSearchChange = (value) => {
    setSearchTerm(value);
  };

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const closeDropdown = () => {
    setTimeout(() => {
      setDropdownOpen(false);
    }, 3000);
  };

  const user = {
    userId: "123",
    userName: "Simanta Sen",
    role: "CUSTOMER",
    authenticated: false,
    accessExpiration: 3600,
    refreshExpiration: 1296000,
  };
  const { userName, role, authenticated } = user;
  if (!authenticated) {
    return (
      <div className="flex items-center justify-around text-slate-100 py-2 text-xl text-center align-middle shadow-xl">
        <div className="translate-x-90">
          <Link to={"/"}>
            <img src={image1} alt="A1" height={40} width={100} />
          </Link>
        </div>
        <div>
          <SearchBar onChange={handleSearchChange} />
        </div>
        <div className="relative" onMouseLeave={closeDropdown}>
          <button
            onMouseOver={toggleDropdown}
            className="text-black h-10 w-30 px-4 bg-white rounded-md focus:outline-none hover:bg-blue-600 hover:text-white"
          >
            <span className="flex items-center">
              <FaRegUserCircle /> &nbsp;&nbsp; Login
            </span>
          </button>
          {dropdownOpen && (
            <div className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-md shadow-lg z-10">
              <div className="flex flex-col gap-2">
                <Link
                  to={"/Login"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <MdLogin className="ml-2" />
                  <div className="ml-4">Login</div>
                </Link>
                <hr />
                <Link
                  to={"/Register"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaRegCircleUser className="ml-2" />
                  <div className="ml-4">Register</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaRegCircleUser className="ml-2" />
                  <div className="ml-4">My Profile</div>
                </Link>

                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaRegPlusSquare className="ml-2" />
                  <div className="ml-4">Flipkart Plus Zone</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FiBox className="ml-2" />
                  <div className="ml-4">Orders</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaBoxOpen className="ml-2" />
                  <div className="ml-4">Rewards</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <BsGift className="ml-2" />
                  <div className="ml-4">Gift Cards</div>
                </Link>
              </div>
            </div>
          )}
        </div>
        <div className="flex items-center">
          <Link to={"/cart"} className="text-black flex items-center">
            <IoCartOutline className="mr-3" /> Cart
          </Link>
        </div>
        <div className="flex items-center">
          <Link to={"/wishList"} className="text-black flex items-center">
            <IoBagHandle className="mr-3" />
            Wishlist
          </Link>
        </div>
      </div>
    );
  } else if (authenticated && role === "CUSTOMER") {
    return (
      <div className="flex items-center justify-around bg-white text-slate-100 py-2 text-xl text-center align-middle shadow-xl">
        <div className="translate-x-90">
          <Link to={"/"}>
            <img src={image1} alt="A1" height={40} width={100} />
          </Link>
        </div>
        <div>
          <SearchBar onChange={handleSearchChange} />
        </div>
        <div className="relative" onMouseLeave={closeDropdown}>
          <button
            onMouseOver={toggleDropdown}
            className="text-black h-10 w-30 px-4 bg-white rounded-md focus:outline-none hover:bg-blue-600 hover:text-white"
          >
            <span className="flex items-center">
              <FaRegUserCircle /> &nbsp;&nbsp; {userName}
            </span>
          </button>
          {dropdownOpen && (
            <div className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-md shadow-lg z-10">
              <div className="flex flex-col gap-2">
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaRegCircleUser className="ml-2" />
                  <div className="ml-4">My Profile</div>
                </Link>

                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaRegPlusSquare className="ml-2" />
                  <div className="ml-4">Flipkart Plus Zone</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FiBox className="ml-2" />
                  <div className="ml-4">Orders</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaBoxOpen className="ml-2" />
                  <div className="ml-4">Rewards</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <BsGift className="ml-2" />
                  <div className="ml-4">Gift Cards</div>
                </Link>
              </div>
            </div>
          )}
        </div>
        <div className="flex items-center">
          <Link to={"/cart"} className="text-black flex items-center">
            <IoCartOutline className="mr-3" /> Cart
          </Link>
        </div>
        <div className="flex items-center">
          <Link to={"/wishList"} className="text-black flex items-center">
            <IoBagHandle className="mr-3" />
            Wishlist
          </Link>
        </div>
      </div>
    );
  } else if (authenticated && role === "SELLER") {
    return (
      <div className="flex items-center justify-around bg-white text-slate-100 py-2 text-xl text-center align-middle shadow-xl">
        <div className="translate-x-90">
          <Link to={"/"}>
            <img src={image1} alt="A1" height={40} width={100} />
          </Link>
        </div>
        <div>
          <SearchBar onChange={handleSearchChange} />
        </div>
        <div className="relative" onMouseLeave={closeDropdown}>
          <button
            onMouseOver={toggleDropdown}
            className="text-black h-10 w-30 px-4 bg-white rounded-md focus:outline-none hover:bg-blue-600 hover:text-white"
          >
            <span className="flex items-center">
              <FaRegUserCircle /> &nbsp;&nbsp; {userName}
            </span>
          </button>
          {dropdownOpen && (
            <div className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-md shadow-lg z-10">
              <div className="flex flex-col gap-2">
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaRegCircleUser className="ml-2" />
                  <div className="ml-4">My Profile</div>
                </Link>

                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaRegPlusSquare className="ml-2" />
                  <div className="ml-4">Flipkart Plus Zone</div>
                </Link>
                <Link
                  to={"/orders"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FiBox className="ml-2" />
                  <div className="ml-4">Orders</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <FaBoxOpen className="ml-2" />
                  <div className="ml-4">Rewards</div>
                </Link>
                <Link
                  to={"/profile"}
                  className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
                >
                  <BsGift className="ml-2" />
                  <div className="ml-4">Gift Cards</div>
                </Link>
              </div>
            </div>
          )}
        </div>
        <Link
          to={"/orders"}
          className="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 flex items-center"
        >
          <FaBox className="ml-2 h-4 w-4" />
          <div className="ml-4">Orders</div>
        </Link>
      </div>
    );
  }
};

export default Header;
