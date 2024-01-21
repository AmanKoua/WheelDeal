import React from "react";

const NavBar = () => {
  return (
    <div className="bg-blue-900 w-full h-12 shadow-md flex flex-row">
      <div className="w-2/12 h-full flex">
        <p className="text-3xl text-blue-100 w-max h-max ml-2 mr-auto mt-auto mb-auto">
          Wheel Deal
        </p>
      </div>
      <div className=" w-2/12 ml-auto h-full flex justify-around">
        <div className="text-blue-100 border border-gray-300 h-3/4 mt-auto mb-auto pl-2 pr-2 rounded-sm shadow-md hover:shadow-lg flex">
          <p className="w-max h-max mt-auto mb-auto">Login</p>
        </div>
        <div className="text-blue-100 border border-gray-300 h-3/4 mt-auto mb-auto pl-2 pr-2 rounded-sm shadow-md hover:shadow-lg flex">
          <p className="w-max h-max mt-auto mb-auto">Signup</p>
        </div>
      </div>
    </div>
  );
};

export default NavBar;
