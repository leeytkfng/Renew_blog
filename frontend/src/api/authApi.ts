import axiosInstance from "./authInstance";
import type {
    LoginRequest,
    LoginResponse,
    RefreshTokenResponse,
    SignupRequest,
    SignupResponse, UserInfo
} from "../type/auth_types";

/**
 * 순수한 API 통신 담당
 * HTTP 요청 응답 처리
 * localStorage 직접 조작
 * 백엔드와 통신하는 저수준 함수
 */
export const authApi = {
    login: async (data: LoginRequest): Promise<LoginResponse> => {
        const response  = await axiosInstance.post<LoginResponse>('/auth/login', data);

        // 토큰 및 사용자 정보 저장
        const { token, userId, email, name } = response.data;
        localStorage.setItem('accessToken', token.accessToken);
        localStorage.setItem('refreshToken', token.refreshToken);
        localStorage.setItem('user', JSON.stringify({ userId, email, name }));

        return response.data;
    },

    signup: async (data: SignupRequest): Promise<SignupResponse> => {
        const response = await axiosInstance.post<SignupResponse>('/auth/signup', data);
        return response.data;
    },

    // 로그아웃
    logout: async (): Promise<void> => {
        try {
            await axiosInstance.post('/auth/logout');
        } finally {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('user');
        }
    },

    //토큰 갱신
    refreshToken : async () : Promise<RefreshTokenResponse> => {
        const refreshToken = localStorage.getItem('refreshToken');

        if (!refreshToken) {
            throw new Error('Refresh token not found');
        }

        const response = await axiosInstance.post<RefreshTokenResponse>(
            '/auth/refresh',
            {refreshToken}
        );


        //새 토큰 저장
        localStorage.setItem('accessToken', response.data.accessToken);
        localStorage.setItem('refreshToken', response.data.refreshToken);

        return response.data;
    },

    getCurrentUser  : () : UserInfo | null => {
        const userStr = localStorage.getItem('user');
        if (!userStr) return null;

        try {
            return JSON.parse(userStr) as UserInfo;
        } catch {
            return null;
        }

    },
        // 인증 여부 확인
        isAuthenticated: (): boolean => {
            const accessToken = localStorage.getItem('accessToken');
            const user = localStorage.getItem('user');
            return !!(accessToken && user);
        },

};