interface ButtonProps {
  label: string;
  onClick: () => void;
  theme?: "PRIMARY" | "SECONDARY";
}

const DefaultButton = ({ label, onClick, theme = "PRIMARY" }: ButtonProps) => {
  const baseClasses = "w-36 px-4 py-2 text-white rounded transition-colors duration-300";
  const themeClasses = theme === "PRIMARY"
    ? "bg-[#1A3AAB] hover:bg-[#0B1B73]"
    : "bg-gray-500 hover:bg-gray-700";

  return (
    <button onClick={onClick} className={`${baseClasses} ${themeClasses}`}>
      {label}
    </button>
  );
};

export default DefaultButton;