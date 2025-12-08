export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    token : {
        accessToken: string;
        refreshToken: string;
    }
    userId: string;
    email: string;
    name: string;
}

export interface UserInfo {
    userId: string;
    email: string;
    name: string;
}

export interface SignupRequest {
    email: string;
    password: string;
    name: string;
    phone: string;
}

export interface SignupResponse {
    id: number;
    email: string;
    name: string;
    message: string;
}

export interface RefreshTokenRequest {
    refreshToken: string;
}

export interface RefreshTokenResponse {
    accessToken: string;
    refreshToken: string;
}


export enum AuthStatus {
    AUTHENTICATED = 'AUTHENTICATED',
    UNAUTHENTICATED = 'UNAUTHENTICATED',
    LOADING = 'LOADING'
}