import { combineReducers } from '@reduxjs/toolkit';
import authSlice from './slices/authSlice';
import reportSlice from './slices/reportSlice';
import evidenceSlice from './slices/evidenceSlice';
import uiSlice from './slices/uiSlice';
import fieldReportSlice from './slices/fieldReportSlice';
import assessmentSlice from './slices/assessmentSlice';
import medicalSupportSlice from './slices/medicalSupportSlice';
import scenePreservationSlice from './slices/scenePreservationSlice';
import statementSlice from './slices/statementSlice';

export const rootReducer = combineReducers({
  auth: authSlice,
  reports: reportSlice,
  evidence: evidenceSlice,
  ui: uiSlice,
  fieldReport: fieldReportSlice,
  assessment: assessmentSlice,
  medicalSupport: medicalSupportSlice,
  scenePreservation: scenePreservationSlice,
  statement: statementSlice,
});