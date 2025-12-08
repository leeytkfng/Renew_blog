export interface AuctionItem {
    id: string;
    title: string;
    location: string;
    currentPrice: number;
    startPrice: number;
    endTime: string;
    imageUrl: string;
    category: string;
    aiScore: number;
}

export interface User {
    userId: string;
    email: string;
    name: string;
}

export interface AuthTokens {
    accessToken: string;
    refreshToken: string;
}