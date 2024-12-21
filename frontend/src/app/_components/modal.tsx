import { useEffect, useState } from "react";
import DefaultButton from "@/app/_components/defaultButton";

interface ModalProps {
  title: string;
  message: string;
  onClose: () => void;
  timer?: number;
}

const Modal = ({ title, message, onClose, timer }: ModalProps) => {
  const [countdown, setCountdown] = useState(timer);

  useEffect(() => {
    if (title === "Success" && countdown && countdown > 0) {
      const interval = setInterval(() => {
        setCountdown((prev) => (prev !== undefined ? prev - 1 : 0));
      }, 1000);
      return () => clearInterval(interval);
    }
  }, [countdown, title]);

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white p-6 rounded shadow-lg w-80">
        <h2 className="text-xl font-bold mb-4">{title}</h2>
        <p className="mb-4">{message}</p>
        {title === "Success" && countdown !== undefined && (
          <p className="mb-4">Redirecting in {countdown}</p>
        )}
        <div className="flex justify-center">
          <DefaultButton label="Ok" onClick={onClose} theme="PRIMARY" />
        </div>
      </div>
    </div>
  );
};

export default Modal;