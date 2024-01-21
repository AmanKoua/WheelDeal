import React from "react";

const Login = () => {
  return (
    <div className="flex" style={{ height: "100vh" }}>
      <div className="bg-gray-200 h-2/4 w-1/4 pb-4 pt-4 rounded-md shadow-xl mt-auto mb-auto ml-auto mr-auto flex flex-col justify-around">
        <p className="w-max p-2 text-2xl font-semibold ml-auto mr-auto">
          Log In
        </p>
        <div className="w-5/6 h-4/6 ml-auto mr-auto flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Email</p>
          <input className="w-full h-7 mr-auto rounded-sm p-1" />
          <p className="w-max p-2 font-semibold mr-auto">Password</p>
          <input
            type="password"
            className="w-full h-7 mr-auto rounded-sm p-1"
          />
        </div>
        <div className="w-full h-10 flex flex-row justify-around">
          <div className="text-black border border-gray-400 h-3/4 mt-auto mb-auto pl-4 pr-4 rounded-sm shadow-md hover:shadow-lg flex">
            <p className="w-max h-max mt-auto mb-auto">Login</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
