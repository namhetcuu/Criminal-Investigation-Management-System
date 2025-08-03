"use client";

import { toast } from "sonner";
import { useState, useEffect } from "react";
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
import Badge from "@/components/ui/badge";
import { useRouter, useSearchParams } from "next/navigation";
import { FileUploadWithPreview, UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";

interface FieldReportFormData {
  reportDetails: string;
  levelAssessment: {
    urgency: "URGENT" | "NOT URGENT";
    description: string;
  };
}

interface FieldReportFormProps {
  initialData?: Partial<FieldReportFormData>;
  onSubmit?: (data: FieldReportFormData) => void;
  isLoading?: boolean;
  onCancel?: () => void;
  onClose?: () => void;
  onSubmitted?: () => void;
}

export function FieldReportForm({
  initialData,
  onSubmit,
  isLoading = false,
  onCancel,
  onClose,
  onSubmitted,
}: FieldReportFormProps) {
  const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>([]);
  const [formData, setFormData] = useState<FieldReportFormData>({
    reportDetails: "",
    levelAssessment: {
      urgency: "NOT URGENT",
      description: "",
    },
    ...initialData,
  });
  
  const router = useRouter();
  const searchParams = useSearchParams();
  const id = searchParams.get("id");
  const [form, setForm] = useState<any>({});

  useEffect(() => {
    if (id && typeof window !== "undefined") {
      const data = JSON.parse(
        sessionStorage.getItem("fieldReports") || "[]"
      );
      const found = data.find((item: any) => String(item.id) === String(id));
      if (found) {
        setForm(found);
        setFormData(found);
      }
    }
  }, [id]);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    if (name === "reportDetails") {
      setFormData((prev) => ({ ...prev, [name]: value }));
    } else if (name === "assessmentDescription") {
      setFormData((prev) => ({
        ...prev,
        levelAssessment: { ...prev.levelAssessment, description: value },
      }));
    }
  };

  const handleSelectChange = (value: "URGENT" | "NOT URGENT") => {
    setFormData((prev) => ({
      ...prev,
      levelAssessment: { ...prev.levelAssessment, urgency: value },
    }));
  };

  const handleSubmitForm = (e: React.FormEvent) => {
    e.preventDefault();
    
    const errors: string[] = [];

    // Validation
    if (!formData.reportDetails || formData.reportDetails.length < 50) {
      errors.push("Report details must be at least 50 characters.");
    }
    if (!formData.levelAssessment.description || formData.levelAssessment.description.length < 20) {
      errors.push("Assessment description must be at least 20 characters.");
    }

    if (errors.length > 0) {
      toast.error("Please fill all required fields:", {
        description: errors.join("\n"),
      });
      return;
    }

    // Save to sessionStorage
    const fieldReports = JSON.parse(
      sessionStorage.getItem("fieldReports") || "[]"
    );
    const newReport = {
      id: fieldReports.length + 1,
      ...formData,
      attachments: uploadedFiles.length > 0 ? `${uploadedFiles.length} files` : "No attachments",
      createdAt: new Date().toISOString(),
    };
    sessionStorage.setItem("fieldReports", JSON.stringify([...fieldReports, newReport]));

    // Success notification
    toast.success("Field report saved successfully!");

    // Call parent callbacks
    if (onSubmit) onSubmit(formData);
    if (onSubmitted) onSubmitted();
    if (onClose) onClose();
  };

  const handleCancelForm = () => {
    if (onCancel) onCancel();
    if (onClose) onClose();
  };

  const handleClearAll = () => {
    setFormData({
      reportDetails: "",
      levelAssessment: {
        urgency: "NOT URGENT",
        description: "",
      },
    });
    setUploadedFiles([]);
  };

  return (
    <div className="space-y-6">
      <form onSubmit={handleSubmitForm} className="space-y-6">
        {/* Report Details White Box */}
        <div className="bg-white p-6 rounded-lg border border-gray-200 shadow-sm">
          <div className="mb-4">
            <Label htmlFor="reportDetails" className="text-lg font-semibold">
              Report Details
            </Label>
          </div>
          <div>
            <Label htmlFor="reportDetails" className="mb-2 block text-sm">
              Detailed Report Information *
            </Label>
            <Textarea
              id="reportDetails"
              name="reportDetails"
              placeholder="Enter detailed report information..."
              className="w-full min-h-[200px]"
              value={formData.reportDetails}
              onChange={handleInputChange}
              required
            />
            <p className="text-xs text-muted-foreground mt-1">
              Minimum 50 characters required
            </p>
          </div>
        </div>

        {/* Level Assessment White Box */}
        <div className="bg-white p-6 rounded-lg border border-gray-200 shadow-sm">
          {/* Header with title and priority dropdown on same level */}
          <div className="flex justify-between items-center mb-4">
            <Label className="text-lg font-semibold">
              Level Assessment
            </Label>
            <div className="flex items-center gap-2">
              <Label htmlFor="urgency" className="text-sm font-medium">
                Severity Level *
              </Label>
              <Select onValueChange={handleSelectChange} value={formData.levelAssessment.urgency}>
                <SelectTrigger id="urgency" className="w-48">
                  <SelectValue placeholder="Select priority level" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="NOT URGENT">
                    <Badge status="Not Urgent" />
                  </SelectItem>
                  <SelectItem value="URGENT">
                    <Badge status="Urgent" />
                  </SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>
          
          <div>
            <Label htmlFor="assessmentDescription" className="mb-2 block text-sm">
              Assessment Description *
            </Label>
            <Textarea
              id="assessmentDescription"
              name="assessmentDescription"
              placeholder="Enter assessment description..."
              className="w-full min-h-[120px]"
              value={formData.levelAssessment.description}
              onChange={handleInputChange}
              required
            />
            <p className="text-xs text-muted-foreground mt-1">
              Minimum 20 characters required
            </p>
          </div>
        </div>

        {/* Images and Videos with FileUploadWithPreview */}
        <FileUploadWithPreview
          uploadedFiles={uploadedFiles}
          onFilesChange={setUploadedFiles}
          isEditing={true}
          accept=".jpg,.jpeg,.png,.gif,.mp4,.pdf,.doc,.docx,.ppt,.pptx"
          maxFiles={10}
          title="Images and Videos"
          description="Supported formats: JPEG, PNG, GIF, MP4, PDF, DOCX, PPT"
        />

        {/* Action Buttons - Same level as other page buttons */}
        <div className="flex justify-end gap-4 bg-white p-4 rounded-lg">
          <Button type="submit" disabled={isLoading} className="rounded-full">
            {isLoading ? "Saving..." : "Save"}
          </Button>
          <Button type="button" variant="outline" onClick={handleCancelForm} className="rounded-full">
            Cancel
          </Button>
        </div>
      </form>
    </div>
  );
}