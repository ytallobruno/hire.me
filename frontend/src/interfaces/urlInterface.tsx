export interface PopularUrlResponse {
  rank: number;
  originalUrl: string;
  hashedUrl: string;
  alias: string;
  accessCount: number;
}

export interface ShortenUrlResponse {
  alias: string;
  originalUrl: string;
  hashedUrl: string;
  statistics: {
    timeTaken: string;
  };
}

export type RetrievedUrlResponse = Omit<PopularUrlResponse, "rank">
