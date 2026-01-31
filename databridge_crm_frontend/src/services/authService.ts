import api from './api';
import type { LoginRequest, RegisterRequest, AuthResponse } from '../types/auth';

export const authService = {

    login: async (credentials: LoginRequest): Promise<AuthResponse> => {
        const response = await api.post<AuthResponse>('/auth/login', credentials);
        return response.data;
    },

    register: async(data: RegisterRequest): Promise<void> => {
        await api.post('/auth/register', data);
    },

    getCurentUser: async () => {
        return null;
    }
}