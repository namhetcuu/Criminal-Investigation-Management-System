import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';

export interface AssessmentFormData {
  id: string;
  location: string;
  collector: string;
  time: string;
  overview: string;
  detailedDescription: string;
  initialCondition: string;
  preservationMeasures: string;
}

interface AssessmentState {
  data: AssessmentFormData | null;
  isLoading: boolean;
  error: string | null;
  isSaving: boolean;
}

const initialState: AssessmentState = {
  data: null,
  isLoading: false,
  error: null,
  isSaving: false,
};

// API calls
export const fetchAssessment = createAsyncThunk(
  'assessment/fetchAssessment',
  async (reportId: string) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    // Mock data - replace with actual API call
    const mockData: AssessmentFormData = {
      id: "PE 01",
      location: "A1 - Kitchen",
      collector: "Lt. James Potter",
      time: "14:25 - 25/06/25",
      overview: "Initial assessment of the kitchen area where the incident occurred. The scene shows signs of disturbance with evidence of a struggle.",
      detailedDescription: "Upon arrival at the scene, the kitchen area displayed clear evidence of a physical altercation. Furniture was overturned, including two dining chairs and a small side table. Glass fragments from a broken drinking glass were scattered across the floor near the sink area. Blood spatter patterns were observed on the wall adjacent to the refrigerator, approximately 3-4 feet from the ground. The victim was found lying prone near the kitchen island with visible injuries to the head and torso. No signs of forced entry were observed through the kitchen windows or back door.",
      initialCondition: "Scene was secured upon arrival. Weather conditions: Clear, temperature 72°F. Lighting: Natural daylight supplemented by overhead kitchen lighting. Access points: Front door (unlocked), back door (locked from inside), two kitchen windows (closed and locked). Time of initial assessment: 14:25 hours. First responders had already established a preliminary perimeter.",
      preservationMeasures: "Immediate establishment of crime scene perimeter using yellow tape in a 30-meter radius around the residence. All personnel required to use designated entry/exit point through front door only. Photographic documentation commenced immediately upon scene assessment. Evidence markers placed for all visible blood spatter and physical evidence. Kitchen area cordoned off as primary scene. Secondary sweep conducted of adjacent rooms. Weather protection measures implemented due to potential for afternoon rain."
    };
    
    return mockData;
  }
);

export const saveAssessment = createAsyncThunk(
  'assessment/saveAssessment',
  async ({ reportId, data }: { reportId: string; data: AssessmentFormData }) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    console.log('Saving assessment:', { reportId, data });
    return data;
  }
);

export const deleteAssessment = createAsyncThunk(
  'assessment/deleteAssessment',
  async (reportId: string) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    console.log('Deleting assessment:', reportId);
    return reportId;
  }
);

const assessmentSlice = createSlice({
  name: 'assessment',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
    updateLocalData: (state, action: PayloadAction<Partial<AssessmentFormData>>) => {
      if (state.data) {
        state.data = { ...state.data, ...action.payload };
      }
    },
    resetAssessment: (state) => {
      state.data = null;
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch assessment
      .addCase(fetchAssessment.pending, (state) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(fetchAssessment.fulfilled, (state, action) => {
        state.isLoading = false;
        state.data = action.payload;
      })
      .addCase(fetchAssessment.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.error.message || 'Failed to fetch assessment';
      })
      // Save assessment
      .addCase(saveAssessment.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(saveAssessment.fulfilled, (state, action) => {
        state.isSaving = false;
        state.data = action.payload;
      })
      .addCase(saveAssessment.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to save assessment';
      })
      // Delete assessment
      .addCase(deleteAssessment.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(deleteAssessment.fulfilled, (state) => {
        state.isSaving = false;
        state.data = null;
      })
      .addCase(deleteAssessment.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to delete assessment';
      });
  },
});

export const { clearError, updateLocalData, resetAssessment } = assessmentSlice.actions;
export default assessmentSlice.reducer;
