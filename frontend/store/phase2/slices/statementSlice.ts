import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { UploadedFile } from '@/components/features/phase2/FileUploadWithPreview';

export interface StatementFormData {
  id?: string;
  personName: string;
  date: string;
  phoneNumber: string;
  role: string;
  detailedStatement: string;
  formType: "witness" | "victim" | "relevant-party";
  files: UploadedFile[];
  createdAt?: string;
  updatedAt?: string;
}

interface StatementState {
  data: { [statementId: string]: StatementFormData };
  isLoading: boolean;
  error: string | null;
  isSaving: boolean;
  currentStatementId: string | null;
}

const initialState: StatementState = {
  data: {},
  isLoading: false,
  error: null,
  isSaving: false,
  currentStatementId: null,
};

// API calls
export const fetchStatements = createAsyncThunk(
  'statement/fetchStatements',
  async (reportId: string) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    // Mock data - replace with actual API call
    const mockData: { [key: string]: StatementFormData } = {};
    
    return mockData;
  }
);

export const fetchStatement = createAsyncThunk(
  'statement/fetchStatement',
  async ({ reportId, statementId }: { reportId: string; statementId: string }) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    // Mock data - replace with actual API call
    const mockData: StatementFormData = {
      id: statementId,
      personName: "",
      date: "",
      phoneNumber: "",
      role: "",
      detailedStatement: "",
      formType: "witness",
      files: [],
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };
    
    return mockData;
  }
);

export const saveStatement = createAsyncThunk(
  'statement/saveStatement',
  async ({ reportId, data }: { reportId: string; data: StatementFormData }) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    const statementWithMetadata = {
      ...data,
      id: data.id || `${data.formType}-${Date.now()}`,
      createdAt: data.createdAt || new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };
    
    console.log('Saving statement:', { reportId, data: statementWithMetadata });
    return statementWithMetadata;
  }
);

export const deleteStatement = createAsyncThunk(
  'statement/deleteStatement',
  async ({ reportId, statementId }: { reportId: string; statementId: string }) => {
    // TODO: Replace with actual API call
    await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate API delay
    
    console.log('Deleting statement:', { reportId, statementId });
    return statementId;
  }
);

const statementSlice = createSlice({
  name: 'statement',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
    setCurrentStatement: (state, action: PayloadAction<string>) => {
      state.currentStatementId = action.payload;
    },
    updateLocalStatement: (state, action: PayloadAction<{ id: string; data: Partial<StatementFormData> }>) => {
      const { id, data } = action.payload;
      if (state.data[id]) {
        state.data[id] = { ...state.data[id], ...data };
      }
    },
    resetStatements: (state) => {
      state.data = {};
      state.error = null;
      state.currentStatementId = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch statements
      .addCase(fetchStatements.pending, (state) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(fetchStatements.fulfilled, (state, action) => {
        state.isLoading = false;
        state.data = action.payload;
      })
      .addCase(fetchStatements.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.error.message || 'Failed to fetch statements';
      })
      // Fetch single statement
      .addCase(fetchStatement.pending, (state) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(fetchStatement.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload.id) {
          state.data[action.payload.id] = action.payload;
          state.currentStatementId = action.payload.id;
        }
      })
      .addCase(fetchStatement.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.error.message || 'Failed to fetch statement';
      })
      // Save statement
      .addCase(saveStatement.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(saveStatement.fulfilled, (state, action) => {
        state.isSaving = false;
        if (action.payload.id) {
          state.data[action.payload.id] = action.payload;
          state.currentStatementId = action.payload.id;
        }
      })
      .addCase(saveStatement.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to save statement';
      })
      // Delete statement
      .addCase(deleteStatement.pending, (state) => {
        state.isSaving = true;
        state.error = null;
      })
      .addCase(deleteStatement.fulfilled, (state, action) => {
        state.isSaving = false;
        delete state.data[action.payload];
        if (state.currentStatementId === action.payload) {
          state.currentStatementId = null;
        }
      })
      .addCase(deleteStatement.rejected, (state, action) => {
        state.isSaving = false;
        state.error = action.error.message || 'Failed to delete statement';
      });
  },
});

export const { clearError, setCurrentStatement, updateLocalStatement, resetStatements } = statementSlice.actions;
export default statementSlice.reducer;
