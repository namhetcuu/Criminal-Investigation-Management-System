import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { UploadedFile } from '@/components/features/phase2/FileUploadWithPreview';

export interface ScenePreservationFormData {
  responsibleUnit: string;
  arrivalTime: string;
  startTime: string;
  endTime: string;
  startIsAM: boolean;
  endIsAM: boolean;
  description: string;
  areaCovered: string;
  notes: string;
  files: UploadedFile[];
}

interface ScenePreservationState {
  data: ScenePreservationFormData | null;
  isLoading: boolean;
  error: string | null;
  isSaving: boolean;
}

const initialState: ScenePreservationState = {
  data: null,
  isLoading: false,
  error: null,
  isSaving: false,
};

// API calls
export const fetchScenePreservation = createAsyncThunk(
  'scenePreservation/fetchScenePreservation',
  async (reportId: string) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    // Mock data - replace with actual API call
    const mockData: ScenePreservationFormData = {
      responsibleUnit: "Unit Alpha-7 - Officer Martinez",
      arrivalTime: "14:25",
      startTime: "14:30",
      endTime: "18:45",
      startIsAM: false,
      endIsAM: false,
      description: "Established perimeter using police tape and traffic cones. Secured all entry points to the scene. Posted officers at strategic locations to prevent unauthorized access. Documented all personnel entering and exiting the scene. Maintained chain of custody for all evidence collected.",
      areaCovered: "Intersection of Main St and Oak Ave - 50m radius perimeter. Covered both eastbound and westbound lanes of Main St, and northbound lane of Oak Ave. Total secured area approximately 7,850 square meters.",
      notes: "Weather conditions: Clear, 72°F. Heavy pedestrian traffic in area required additional crowd control measures. Media personnel kept at designated distance of 100m from scene perimeter. Local businesses cooperated with temporary access restrictions.",
      files: []
    };
    
    return mockData;
  }
);

export const saveScenePreservation = createAsyncThunk(
  'scenePreservation/saveScenePreservation',
  async ({ reportId, data }: { reportId: string; data: ScenePreservationFormData }) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    console.log('Saving scene preservation:', { reportId, data });
    return data;
  }
);

export const deleteScenePreservation = createAsyncThunk(
  'scenePreservation/deleteScenePreservation',
  async (reportId: string) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    console.log('Deleting scene preservation:', reportId);
    return reportId;
  }
);

const scenePreservationSlice = createSlice({
  name: 'scenePreservation',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
    updateLocalData: (state, action: PayloadAction<Partial<ScenePreservationFormData>>) => {
      if (state.data) {
        state.data = { ...state.data, ...action.payload };
      }
    },
    resetScenePreservation: (state) => {
      state.data = null;
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch scene preservation
      .addCase(fetchScenePreservation.pending, (state) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(fetchScenePreservation.fulfilled, (state, action) => {
        state.isLoading = false;
        state.data = action.payload;
      })
      .addCase(fetchScenePreservation.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.error.message || 'Failed to fetch scene preservation';
      })
      // Save scene preservation
      .addCase(saveScenePreservation.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(saveScenePreservation.fulfilled, (state, action) => {
        state.isSaving = false;
        state.data = action.payload;
      })
      .addCase(saveScenePreservation.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to save scene preservation';
      })
      // Delete scene preservation
      .addCase(deleteScenePreservation.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(deleteScenePreservation.fulfilled, (state) => {
        state.isSaving = false;
        state.data = null;
      })
      .addCase(deleteScenePreservation.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to delete scene preservation';
      });
  },
});

export const { clearError, updateLocalData, resetScenePreservation } = scenePreservationSlice.actions;
export default scenePreservationSlice.reducer;
