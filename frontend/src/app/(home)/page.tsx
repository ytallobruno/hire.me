"use client";

import { useRouter } from "next/navigation";
import DefaultButton from "@/app/_components/defaultButton";
import Popular from "@/app/(home)/_components/popular";

const Home = () => {
  const router = useRouter();

  const handleShortenUrl = () => {
    router.push("/shortener");
  };

  const handleRetrieveUrl = () => {
    router.push("/retrieve");
  };

  return (
    <>
      <div className="flex flex-col items-center justify-center h-full space-y-4 p-8 mt-20">
        <h1 className="text-2xl font-bold">Hire.me By Bemobi</h1>
        <DefaultButton label="Shorten URL" onClick={handleShortenUrl} />
        <DefaultButton label="Retrieve URL" onClick={handleRetrieveUrl} />
        <Popular />
      </div>
    </>
  );
};

export default Home;
