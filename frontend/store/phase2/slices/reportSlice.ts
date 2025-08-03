import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';

export interface Report {
  id: string;
  reportDetails: string;
  levelAssessment: {
    urgency: 'URGENT' | 'NOT URGENT';
    description: string;
  };
  mediaFiles: MediaFile[];
  createdAt: string;
  updatedAt: string;
}

interface MediaFile {
  fileName: string;
  type: 'Image' | 'Video';
  capturedBy: string;
  timestamp: string;
  size: string;
}

interface ReportState {
  reports: Report[];
  currentReport: Report | null;
  isLoading: boolean;
  isEditing: boolean;
  error: string | null;
}

const initialState: ReportState = {
  reports: [],
  currentReport: null,
  isLoading: false,
  isEditing: false,
  error: null,
};

// Async thunks for API calls
export const fetchReport = createAsyncThunk(
  'reports/fetchReport',
  async (reportId: string) => {
    const response = await fetch(`/api/reports/${reportId}`);
    return response.json();
  }
);

export const saveReport = createAsyncThunk(
  'reports/saveReport',
  async (report: Partial<Report>) => {
    const response = await fetch(`/api/reports/${report.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(report),
    });
    return response.json();
  }
);

const reportSlice = createSlice({
  name: 'reports',
  initialState,
  reducers: {
    setIsEditing: (state, action: PayloadAction<boolean>) => {
      state.isEditing = action.payload;
    },
    updateReportDetails: (state, action: PayloadAction<string>) => {
      if (state.currentReport) {
        state.currentReport.reportDetails = action.payload;
      }
    },
    updateLevelAssessment: (state, action: PayloadAction<{ field: string; value: string }>) => {
      if (state.currentReport) {
        const { field, value } = action.payload;
        if (field === 'urgency') {
          state.currentReport.levelAssessment.urgency = value as 'URGENT' | 'NOT URGENT';
        } else if (field === 'description') {
          state.currentReport.levelAssessment.description = value;
        }
      }
    },
    clearReport: (state) => {
      state.currentReport = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchReport.pending, (state) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(fetchReport.fulfilled, (state, action) => {
        state.isLoading = false;
        state.currentReport = action.payload;
      })
      .addCase(fetchReport.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.error.message || 'Failed to fetch report';
      })
      .addCase(saveReport.fulfilled, (state, action) => {
        state.currentReport = action.payload;
        state.isEditing = false;
      });
  },
});

export const { setIsEditing, updateReportDetails, updateLevelAssessment, clearReport } = reportSlice.actions;
export default reportSlice.reducer;