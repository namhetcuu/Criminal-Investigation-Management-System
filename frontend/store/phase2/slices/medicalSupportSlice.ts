import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { UploadedFile } from '@/components/features/phase2/FileUploadWithPreview';

export interface MedicalSupportFormData {
  unitId: string;
  supportType: string;
  personnelAssigned: string;
  locationAssigned: string;
  remarks: string;
  files: UploadedFile[];
}

interface MedicalSupportState {
  data: MedicalSupportFormData | null;
  isLoading: boolean;
  error: string | null;
  isSaving: boolean;
}

const initialState: MedicalSupportState = {
  data: null,
  isLoading: false,
  error: null,
  isSaving: false,
};

// API calls
export const fetchMedicalSupport = createAsyncThunk(
  'medicalSupport/fetchMedicalSupport',
  async (reportId: string) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    // Mock data - replace with actual API call
    const mockData: MedicalSupportFormData = {
      unitId: "EMS-001",
      supportType: "Emergency Medical Response",
      personnelAssigned: "Dr. Sarah Johnson - Lead Paramedic\nMark Williams - EMT\nLisa Chen - Emergency Nurse",
      locationAssigned: "Scene Perimeter - Ambulance Station Alpha",
      remarks: "Medical team arrived at 14:30 and established triage area. Two patients transported to General Hospital with non-life-threatening injuries. Scene remained secure throughout medical operations.",
      files: []
    };
    
    return mockData;
  }
);

export const saveMedicalSupport = createAsyncThunk(
  'medicalSupport/saveMedicalSupport',
  async ({ reportId, data }: { reportId: string; data: MedicalSupportFormData }) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    console.log('Saving medical support:', { reportId, data });
    return data;
  }
);

export const deleteMedicalSupport = createAsyncThunk(
  'medicalSupport/deleteMedicalSupport',
  async (reportId: string) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    console.log('Deleting medical support:', reportId);
    return reportId;
  }
);

const medicalSupportSlice = createSlice({
  name: 'medicalSupport',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
    updateLocalData: (state, action: PayloadAction<Partial<MedicalSupportFormData>>) => {
      if (state.data) {
        state.data = { ...state.data, ...action.payload };
      }
    },
    resetMedicalSupport: (state) => {
      state.data = null;
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch medical support
      .addCase(fetchMedicalSupport.pending, (state) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(fetchMedicalSupport.fulfilled, (state, action) => {
        state.isLoading = false;
        state.data = action.payload;
      })
      .addCase(fetchMedicalSupport.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.error.message || 'Failed to fetch medical support';
      })
      // Save medical support
      .addCase(saveMedicalSupport.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(saveMedicalSupport.fulfilled, (state, action) => {
        state.isSaving = false;
        state.data = action.payload;
      })
      .addCase(saveMedicalSupport.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to save medical support';
      })
      // Delete medical support
      .addCase(deleteMedicalSupport.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(deleteMedicalSupport.fulfilled, (state) => {
        state.isSaving = false;
        state.data = null;
      })
      .addCase(deleteMedicalSupport.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to delete medical support';
      });
  },
});

export const { clearError, updateLocalData, resetMedicalSupport } = medicalSupportSlice.actions;
export default medicalSupportSlice.reducer;
