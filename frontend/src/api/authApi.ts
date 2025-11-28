import axiosInstance from "./authInstance";
import type { LoginRequest, LoginResponse, SignupRequest, SignupResponse } from "../type/auth_types";

//3
export const authApi = {
    login: async (data: LoginRequest): Promise<LoginResponse> => {
        const response = await axiosInstance.post<LoginResponse>('/auth/login', data);
        return response.data;
    },

    signup: async (data: SignupRequest): Promise<SignupResponse> => {
        const response = await axiosInstance.post<SignupResponse>('/auth/signup', data);
        return response.data;
    },

    logout: async (): Promise<void> => {
        await axiosInstance.post('/auth/logout');
        localStorage.clear();
    },
};