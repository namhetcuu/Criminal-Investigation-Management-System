// store/phase2/slices/fieldReportSlice.ts
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

// API calls
export const fetchFieldReport = createAsyncThunk(
  'fieldReport/fetchFieldReport',
  async (reportId: string) => {
    const response = await fetch(`/api/reports/${reportId}/field-report`);
    if (!response.ok) throw new Error('Failed to fetch field report');
    return response.json();
  }
);

export const saveFieldReport = createAsyncThunk(
  'fieldReport/saveFieldReport', 
  async ({ reportId, data }: { reportId: string; data: any }) => {
    const response = await fetch(`/api/reports/${reportId}/field-report`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error('Failed to save field report');
    return response.json();
  }
);

interface FieldReportState {
  data: any | null;
  isLoading: boolean;
  error: string | null;
  isSaving: boolean;
}

const initialState: FieldReportState = {
  data: null,
  isLoading: false,
  error: null,
  isSaving: false,
};

const fieldReportSlice = createSlice({
  name: 'fieldReport',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch field report
      .addCase(fetchFieldReport.pending, (state) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(fetchFieldReport.fulfilled, (state, action) => {
        state.isLoading = false;
        state.data = action.payload;
      })
      .addCase(fetchFieldReport.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.error.message || 'Failed to fetch field report';
      })
      // Save field report
      .addCase(saveFieldReport.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(saveFieldReport.fulfilled, (state, action) => {
        state.isSaving = false;
        state.data = action.payload;
      })
      .addCase(saveFieldReport.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to save field report';
      });
  },
});

export const { clearError } = fieldReportSlice.actions;
export default fieldReportSlice.reducer;