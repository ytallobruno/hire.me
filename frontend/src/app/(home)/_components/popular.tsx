import { useEffect, useState } from "react";
import { retrieveUrl, getPopularUrls } from "@/app/_actions/urlAction";
import { PopularUrlResponse } from "@/interfaces/urlInterface";
import Spinner from "@/app/_components/spinner";
import axios from "axios";

const Popular = () => {
  const [urls, setUrls] = useState<PopularUrlResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    const fetchPopularUrls = async () => {
      try {
        const data = await getPopularUrls();
        setUrls(data);
      } catch (error) {
        console.error("Error fetching popular URLs:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPopularUrls();
  }, [refresh]);

  const handleCardClick = async (alias: string) => {
    try {
      const originalUrl = await retrieveUrl(alias);
      window.open(originalUrl, "_blank");
      setRefresh((prev) => !prev);
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        alert("SHORTENED URL NOT FOUND");
      } else {
        alert("Error retrieving URL, try again");
      }
    }
  };

  return (
    <div className="flex flex-col items-center space-y-4">
      <h2 className="text-2xl font-bold text-center mb-4">Top 10 URLs</h2>
      {loading ? (
        <Spinner />
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
          {urls.map((url) => (
            <div
              key={url.alias}
              onClick={() => handleCardClick(url.hashedUrl)}
              className="p-6 border rounded-lg shadow-md bg-white transform transition-transform hover:scale-105 cursor-pointer"
            >
              <h3 className="text-lg font-semibold text-gray-600 mb-2">
                Rank: {url.rank}
              </h3>
              <p className="text-sm text-gray-600 mb-2">
                <b>Alias</b>: {url.alias}
              </p>
              <p className="text-sm text-gray-600 mb-2">
                <b>Access Count</b>: {url.accessCount}
              </p>
              <p className="text-sm text-gray-600 mb-2 truncate" title={url.originalUrl}>
                <b>Original url</b>: {url.originalUrl}
              </p>
              <p className="text-blue-500 block truncate">{url.hashedUrl}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Popular;