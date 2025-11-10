export interface LoginRequest {
    email: string;
    password: string;
}

export interface SignupRequest {
    email: string;
    password: string;
    name: string;
}

export interface TokenResponse {
    accessToken : string;
    refreshToken? : string;
    tokenType : string;
    expiresIn: number;
}

export interface AuthResponse {
    user : {
        id: string;
        email: string;
        name: string;
        role: string;
    };
    token: TokenResponse;
}