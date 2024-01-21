import { useState, useEffect } from "react";

interface Props {
  isError: boolean;
  message: String;
}

const Notification = ({ isError, message }: Props) => {
  const [color, setColor] = useState("");
  const [border, setBorder] = useState("");

  useEffect(() => {
    if (isError) {
      setColor("bg-red-400");
      setBorder("border-2 border-red-500");
    } else {
      setColor("bg-green-400");
      setBorder("border-2 border-green-500");
    }
  }, []);

  return (
    <div
      className={`${color} ${border} text-center font-semibold h-max w-1/4 pb-4 pt-4 pl-2 pr-2 rounded-md shadow-xl mb-auto ml-auto mr-auto flex flex-col justify-around`}
    >
      {message}
    </div>
  );
};

export default Notification;
