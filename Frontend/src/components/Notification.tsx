import { useState, useEffect } from "react";

interface Props {
  isError: boolean;
  message: String;
}

const Notification = ({ isError, message }: Props) => {
  const className = isError
    ? "bg-red-400 border-2 border-red-500 text-center font-semibold h-max w-1/4 pb-4 pt-4 pl-2 pr-2 rounded-md shadow-xl mb-auto ml-auto mr-auto flex flex-col justify-around"
    : "bg-green-400 border-2 border-green-500 text-center font-semibold h-max w-1/4 pb-4 pt-4 pl-2 pr-2 rounded-md shadow-xl mb-auto ml-auto mr-auto flex flex-col justify-around";

  return <div className={className}>{message}</div>;
};

export default Notification;
