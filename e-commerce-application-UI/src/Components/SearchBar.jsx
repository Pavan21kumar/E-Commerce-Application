import React from 'react';
import { BsSearch } from 'react-icons/bs'; // Importing the search icon from react-icons library

const SearchBar = ({ onChange }) => {
  return (
    <div className="flex items-center">
      <BsSearch className="text-gray-400 h-6 w-6 mr-2" /> {/* Increased size of search icon */}
      <input
        type="text"
        placeholder="Search for Products, Brands And More"
        onChange={(e) => onChange(e.target.value)}
        className="w-124 h-12 text-black bg-blue-100 px-5 rounded-md relative text-base placeholder:text-black" // Increased size of input
      />
    </div>
  );
};

export default SearchBar;

 