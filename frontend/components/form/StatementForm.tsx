"use client";

import { toast } from "sonner";
import { useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { FileUploadWithPreview, UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";

interface StatementFormData {
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

interface StatementFormProps {
  initialData?: Partial<StatementFormData>;
  onSubmit?: (data: StatementFormData) => void;
  isLoading?: boolean;
  isEditing?: boolean;
  onEdit?: () => void;
  onCancel?: () => void;
  onClose?: () => void;
  formType?: "witness" | "victim" | "relevant-party";
}

const roleOptions = {
  witness: [
    { value: "Witness", label: "Witness" },
    { value: "Bystander", label: "Bystander" },
    { value: "First Responder", label: "First Responder" },
  ],
  victim: [
    { value: "Primary Victim", label: "Primary Victim" },
    { value: "Secondary Victim", label: "Secondary Victim" },
    { value: "Family Member", label: "Family Member" },
  ],
  "relevant-party": [
    { value: "Property Owner", label: "Property Owner" },
    { value: "Business Owner", label: "Business Owner" },
    { value: "Insurance Representative", label: "Insurance Representative" },
    { value: "Legal Representative", label: "Legal Representative" },
    { value: "Expert Witness", label: "Expert Witness" },
    { value: "Other", label: "Other" },
  ],
};

const formTitles = {
  witness: "Witness Statement",
  victim: "Victim Statement", 
  "relevant-party": "Relevant Party Statement",
};

export function StatementForm({
  initialData,
  onSubmit,
  isLoading = false,
  isEditing = false,
  onEdit,
  onCancel,
  onClose,
  formType = "witness",
}: StatementFormProps) {
  const [formData, setFormData] = useState<StatementFormData>({
    id: initialData?.id || undefined,
    personName: "",
    date: "",
    phoneNumber: "",
    role: "",
    detailedStatement: "",
    formType,
    files: [],
    ...initialData,
  });

  const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>(initialData?.files || []);

  // Update form data when initialData changes
  useEffect(() => {
    if (initialData) {
      setFormData({
        id: initialData.id || undefined,
        personName: initialData.personName || "",
        date: initialData.date || "",
        phoneNumber: initialData.phoneNumber || "",
        role: initialData.role || "",
        detailedStatement: initialData.detailedStatement || "",
        formType,
        files: initialData.files || [],
        createdAt: initialData.createdAt,
        updatedAt: initialData.updatedAt,
      });
      setUploadedFiles(initialData.files || []);
    }
  }, [initialData, formType]);

  const handleInputChange = (field: keyof StatementFormData, value: string) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  const handleSubmitForm = (e: React.FormEvent) => {
    e.preventDefault();
    
    const errors: string[] = [];

    // Validation
    if (!formData.personName.trim()) {
      errors.push("Person name is required.");
    }
    if (!formData.date.trim()) {
      errors.push("Date is required.");
    }
    if (!formData.phoneNumber.trim()) {
      errors.push("Phone number is required.");
    }
    if (!formData.role.trim()) {
      errors.push("Role is required.");
    }
    if (!formData.detailedStatement.trim() || formData.detailedStatement.length < 20) {
      errors.push("Detailed statement must be at least 20 characters.");
    }

    if (errors.length > 0) {
      toast.error("Please fill all required fields:", {
        description: errors.join("\n"),
      });
      return;
    }

    // Prepare the complete form data with metadata
    const completeFormData: StatementFormData = {
      ...formData,
      id: formData.id || `${formType}-${Date.now()}`, // Generate ID if new
      files: uploadedFiles,
      formType,
      createdAt: formData.createdAt || new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };

    // Success notification
    toast.success(`${formTitles[formType]} saved successfully!`);

    // Return the structured data to parent
    if (onSubmit) {
      onSubmit(completeFormData);
    }

    if (onClose) onClose();
  };

  const handleCancelForm = () => {
    // Clean up preview URLs
    uploadedFiles.forEach(uploadedFile => {
      if (uploadedFile.preview) {
        URL.revokeObjectURL(uploadedFile.preview);
      }
    });

    if (onCancel) onCancel();
    if (onClose) onClose();
  };

  const handleClearAll = () => {
    // Clean up preview URLs
    uploadedFiles.forEach(uploadedFile => {
      if (uploadedFile.preview) {
        URL.revokeObjectURL(uploadedFile.preview);
      }
    });

    setFormData({
      id: undefined,
      personName: "",
      date: "",
      phoneNumber: "",
      role: "",
      detailedStatement: "",
      formType,
      files: [],
    });
    setUploadedFiles([]);
  };

  const handleFilesChange = (files: UploadedFile[]) => {
    setUploadedFiles(files);
  };

  if (!isEditing) {
    // Display mode
    return (
      <div className="space-y-6">
        {/* Person Information Display */}
        <div className="bg-white rounded-lg p-6 border border-gray-200 shadow-sm">
          <h2 className="text-xl font-bold text-gray-900 mb-4">{formTitles[formType]} Information</h2>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Name</label>
              <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                {formData.personName || <span className="text-gray-500 italic">No name specified</span>}
              </div>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Date</label>
              <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                {formData.date ? new Date(formData.date).toLocaleDateString() : <span className="text-gray-500 italic">No date specified</span>}
              </div>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Contact Information (Phone Number)</label>
              <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                {formData.phoneNumber || <span className="text-gray-500 italic">No phone number specified</span>}
              </div>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Role</label>
              <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                {formData.role || <span className="text-gray-500 italic">No role specified</span>}
              </div>
            </div>
          </div>
        </div>

        {/* Detailed Statement Display */}
        <div className="bg-white rounded-lg p-6 border border-gray-200 shadow-sm">
          <h2 className="text-xl font-bold text-gray-900 mb-4">Detailed Statement</h2>
          <div className="text-sm text-gray-900 bg-gray-50 p-4 rounded-lg leading-relaxed min-h-[12rem] whitespace-pre-line">
            {formData.detailedStatement || <span className="text-gray-500 italic">No detailed statement provided</span>}
          </div>
        </div>

        {/* Evidence Files Display */}
        <FileUploadWithPreview
          uploadedFiles={uploadedFiles}
          onFilesChange={handleFilesChange}
          isEditing={false}
          title="Evidence Files"
          description="Supports: JPG, PNG, PDF, DOC, MP3, MP4, MOV files"
          accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.mp3,.mp4,.mov,.wav"
          maxFiles={15}
        />

        <div className="flex justify-end gap-4 bg-white p-4 rounded-lg">
          <Button onClick={onEdit}>Edit Statement</Button>
        </div>
      </div>
    );
  }

  // Edit mode
  return (
    <div className="space-y-6">
      <form onSubmit={handleSubmitForm} className="space-y-6">
        {/* Person Information White Box */}
        <div className="bg-white rounded-lg p-6 border border-gray-200 shadow-sm">
          <h2 className="text-xl font-bold text-gray-900 mb-4">{formTitles[formType]} Information</h2>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="personName" className="block text-sm font-medium text-gray-700 mb-1">
                Name *
              </Label>
              <Input
                id="personName"
                value={formData.personName}
                onChange={(e) => handleInputChange('personName', e.target.value)}
                placeholder="Enter name..."
                className="w-full rounded-lg"
                required
              />
            </div>
            <div>
              <Label htmlFor="date" className="block text-sm font-medium text-gray-700 mb-1">
                Date *
              </Label>
              <Input
                id="date"
                type="date"
                value={formData.date}
                onChange={(e) => handleInputChange('date', e.target.value)}
                className="w-full rounded-lg"
                required
              />
            </div>
            <div>
              <Label htmlFor="phoneNumber" className="block text-sm font-medium text-gray-700 mb-1">
                Contact Information (Phone Number) *
              </Label>
              <Input
                id="phoneNumber"
                value={formData.phoneNumber}
                onChange={(e) => handleInputChange('phoneNumber', e.target.value)}
                placeholder="Enter phone number..."
                className="w-full rounded-lg"
                required
              />
            </div>
            <div>
              <Label htmlFor="role" className="block text-sm font-medium text-gray-700 mb-1">
                Role *
              </Label>
              <Select onValueChange={(value) => handleInputChange('role', value)} value={formData.role}>
                <SelectTrigger id="role" className="w-full">
                  <SelectValue placeholder="Select role..." />
                </SelectTrigger>
                <SelectContent>
                  {roleOptions[formType].map((option) => (
                    <SelectItem key={option.value} value={option.value}>
                      {option.label}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </div>
        </div>

        {/* Detailed Statement White Box */}
        <div className="bg-white rounded-lg p-6 border border-gray-200 shadow-sm">
          <h2 className="text-xl font-bold text-gray-900 mb-4">Detailed Statement</h2>
          <div>
            <Label htmlFor="detailedStatement" className="block text-sm font-medium text-gray-700 mb-2">
              Detailed Statement *
            </Label>
            <Textarea
              id="detailedStatement"
              value={formData.detailedStatement}
              onChange={(e) => handleInputChange('detailedStatement', e.target.value)}
              className="w-full rounded-lg border-gray-300"
              rows={8}
              placeholder={`Enter detailed ${formType} statement...`}
              required
            />
            <p className="text-xs text-muted-foreground mt-1">
              Minimum 20 characters required
            </p>
          </div>
        </div>

        {/* Evidence Files Section */}
        <FileUploadWithPreview
          uploadedFiles={uploadedFiles}
          onFilesChange={handleFilesChange}
          isEditing={true}
          title="Evidence Files"
          description="Supports: JPG, PNG, PDF, DOC, MP3, MP4, MOV files"
          accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.mp3,.mp4,.mov,.wav"
          maxFiles={15}
        />

        {/* Action Buttons */}
        <div className="flex justify-end gap-4 bg-white p-4 rounded-lg">
          <Button type="button" variant="outline" onClick={handleClearAll} className="rounded-full text-red-600 hover:text-red-700">
            Clear All
          </Button>
          <Button type="submit" disabled={isLoading} className="rounded-full">
            {isLoading ? "Saving..." : "Save Statement"}
          </Button>
          <Button type="button" variant="outline" onClick={handleCancelForm} className="rounded-full">
            Cancel
          </Button>
        </div>
      </form>
    </div>
  );
}

// Export the data interface for use in parent components
export type { StatementFormData };