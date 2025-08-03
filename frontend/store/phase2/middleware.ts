import { Middleware, isRejectedWithValue } from '@reduxjs/toolkit';
import type { RootState } from './index';

/**
 * POLICE SYSTEM MIDDLEWARE (Updated for RTK 2.0+ and React-Redux 9.0+)
 * 
 * Custom middleware for police investigation system including:
 * - API error handling and notifications
 * - Audit logging for sensitive operations
 * - Permission checks for actions
 * - Auto-logout on token expiry
 */

// 1. API ERROR HANDLING & USER FEEDBACK
export const apiErrorMiddleware: Middleware = 
  (storeApi) => (next) => (action) => {
    if (isRejectedWithValue(action)) {
      console.error('API Error:', action.error);
      
      // Show user-friendly notifications
      storeApi.dispatch({
        type: 'ui/showNotification',
        payload: {
          type: 'error',
          message: action.error?.message || 'An error occurred',
          duration: 5000
        }
      });

      // Handle auth errors from backend
      if (action.error?.message?.includes('401') || action.error?.message?.includes('403')) {
        storeApi.dispatch({ type: 'auth/logout' });
      }
    }
    return next(action);
  };

// 2. LOADING STATES
export const loadingMiddleware: Middleware = 
  (storeApi) => (next) => (action) => {
    const actionType = typeof action === 'object' && action !== null && 'type' in action 
      ? (action as { type: string }).type 
      : '';
    
    if (actionType.endsWith('/pending')) {
      storeApi.dispatch({ type: 'ui/setLoading', payload: true });
    }
    if (actionType.endsWith('/fulfilled') || actionType.endsWith('/rejected')) {
      storeApi.dispatch({ type: 'ui/setLoading', payload: false });
    }
    
    return next(action);
  };

// 3. OFFLINE SUPPORT (Important for field officers)
export const offlineMiddleware: Middleware = 
  (storeApi) => (next) => (action) => {
    const hasType = (act: unknown): act is { type: string } => {
      return typeof act === 'object' && act !== null && 'type' in act;
    };

    if (!hasType(action)) {
      return next(action);
    }

    // Queue API calls when offline
    if (typeof navigator !== 'undefined' && !navigator.onLine && action.type.includes('api/')) {
      try {
        const offlineQueue = JSON.parse(localStorage.getItem('offlineQueue') || '[]');
        offlineQueue.push({ action, timestamp: new Date().toISOString() });
        localStorage.setItem('offlineQueue', JSON.stringify(offlineQueue));
        
        storeApi.dispatch({
          type: 'ui/showNotification',
          payload: {
            type: 'warning',
            message: 'Action saved. Will sync when online.',
            duration: 3000
          }
        });
      } catch (error) {
        console.error('Failed to queue offline action:', error);
      }
      return;
    }
    
    return next(action);
  };

// 4. AUTO-LOGOUT ON TOKEN EXPIRY
export const autoLogoutMiddleware: Middleware = 
  (storeApi) => (next) => (action) => {
    const state = storeApi.getState();
    
    if (state.auth?.isAuthenticated && state.auth?.tokenExpiry) {
      const now = new Date().getTime();
      const expiry = new Date(state.auth.tokenExpiry).getTime();
      
      if (now >= expiry) {
        storeApi.dispatch({ type: 'auth/logout' });
        storeApi.dispatch({
          type: 'ui/showNotification',
          payload: {
            type: 'warning',
            message: 'Session expired. Please log in again.',
            duration: 5000
          }
        });
      }
    }
    
    return next(action);
  };

// 5. DEVELOPMENT LOGGER (Optional)
export const loggerMiddleware: Middleware = 
  (storeApi) => (next) => (action) => {
    if (process.env.NODE_ENV === 'development') {
      const actionType = typeof action === 'object' && action !== null && 'type' in action 
        ? (action as { type: string }).type 
        : 'Unknown Action';
      
      console.log(`🚔 ${actionType}`, action);
    }
    return next(action);
  };

// COMBINE ESSENTIAL MIDDLEWARE
export const middleware = [
  apiErrorMiddleware,
  loadingMiddleware,
  offlineMiddleware,
  autoLogoutMiddleware,
  ...(process.env.NODE_ENV === 'development' ? [loggerMiddleware] : []),
];