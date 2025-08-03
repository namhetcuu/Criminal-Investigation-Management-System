"use client";

import { toast } from "sonner";
import { useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { useRouter, useSearchParams } from "next/navigation";

interface EvidenceFormData {
  id: string;
  location: string;
  collector: string;
  time: string;
  overview: string;
  detailedDescription: string;
  initialCondition: string;
  preservationMeasures: string;
}

interface EvidenceFormProps {
  initialData?: Partial<EvidenceFormData>;
  onSubmit?: (data: EvidenceFormData) => void;
  isLoading?: boolean;
  onCancel?: () => void;
  onClose?: () => void;
  onSubmitted?: () => void;
}

export function EvidenceForm({
  initialData,
  onSubmit,
  isLoading = false,
  onCancel,
  onClose,
  onSubmitted,
}: EvidenceFormProps) {
  const [formData, setFormData] = useState<EvidenceFormData>({
    id: "",
    location: "",
    collector: "",
    time: "",
    overview: "",
    detailedDescription: "",
    initialCondition: "",
    preservationMeasures: "",
    ...initialData,
  });

  const router = useRouter();
  const searchParams = useSearchParams();
  const evidenceId = searchParams.get("id");

  useEffect(() => {
    if (evidenceId && typeof window !== "undefined") {
      const data = JSON.parse(
        sessionStorage.getItem("evidenceRecords") || "[]"
      );
      const found = data.find((item: any) => String(item.id) === String(evidenceId));
      if (found) {
        setFormData(found);
      }
    }
  }, [evidenceId]);

  const handleInputChange = (field: keyof EvidenceFormData, value: string) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  const handleSubmitForm = (e: React.FormEvent) => {
    e.preventDefault();
    
    const errors: string[] = [];

    // Validation
    if (!formData.id.trim()) {
      errors.push("Evidence ID is required.");
    }
    if (!formData.location.trim()) {
      errors.push("Location is required.");
    }
    if (!formData.collector.trim()) {
      errors.push("Collector name is required.");
    }
    if (!formData.time.trim()) {
      errors.push("Time is required.");
    }
    if (!formData.overview.trim() || formData.overview.length < 10) {
      errors.push("Overview must be at least 10 characters.");
    }
    if (!formData.detailedDescription.trim() || formData.detailedDescription.length < 20) {
      errors.push("Detailed description must be at least 20 characters.");
    }

    if (errors.length > 0) {
      toast.error("Please fill all required fields:", {
        description: errors.join("\n"),
      });
      return;
    }

    // Save to sessionStorage
    const evidenceRecords = JSON.parse(
      sessionStorage.getItem("evidenceRecords") || "[]"
    );
    
    const existingIndex = evidenceRecords.findIndex((item: any) => item.id === formData.id);
    const evidenceRecord = {
      ...formData,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };

    if (existingIndex >= 0) {
      // Update existing record
      evidenceRecords[existingIndex] = { ...evidenceRecords[existingIndex], ...evidenceRecord };
    } else {
      // Add new record
      evidenceRecords.push(evidenceRecord);
    }

    sessionStorage.setItem("evidenceRecords", JSON.stringify(evidenceRecords));

    // Success notification
    toast.success("Evidence record saved successfully!");

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
      id: "",
      location: "",
      collector: "",
      time: "",
      overview: "",
      detailedDescription: "",
      initialCondition: "",
      preservationMeasures: "",
    });
  };

  return (
    <div className="space-y-6">
      <form onSubmit={handleSubmitForm} className="space-y-6">
        {/* Overview Section White Box */}
        <div className="bg-white p-6 rounded-lg border border-gray-200 shadow-sm">
          <div className="mb-4">
            <Label className="text-lg font-semibold">
              Overview
            </Label>
          </div>
          
          <div className="grid grid-cols-2 gap-4 mb-4">
            <div>
              <Label htmlFor="evidenceId" className="block text-sm font-medium text-gray-700 mb-1">
                Evidence ID *
              </Label>
              <Input
                id="evidenceId"
                value={formData.id}
                onChange={(e) => handleInputChange('id', e.target.value)}
                placeholder="Enter evidence ID..."
                className="w-full rounded-lg"
                required
              />
            </div>
            <div>
              <Label htmlFor="location" className="block text-sm font-medium text-gray-700 mb-1">
                Location *
              </Label>
              <Input
                id="location"
                value={formData.location}
                onChange={(e) => handleInputChange('location', e.target.value)}
                placeholder="Enter location..."
                className="w-full rounded-lg"
                required
              />
            </div>
            <div>
              <Label htmlFor="collector" className="block text-sm font-medium text-gray-700 mb-1">
                Collector *
              </Label>
              <Input
                id="collector"
                value={formData.collector}
                onChange={(e) => handleInputChange('collector', e.target.value)}
                placeholder="Enter collector name..."
                className="w-full rounded-lg"
                required
              />
            </div>
            <div>
              <Label htmlFor="time" className="block text-sm font-medium text-gray-700 mb-1">
                Time *
              </Label>
              <Input
                id="time"
                value={formData.time}
                onChange={(e) => handleInputChange('time', e.target.value)}
                placeholder="Enter time..."
                className="w-full rounded-lg"
                required
              />
            </div>
          </div>
          
          <div>
            <Label htmlFor="overview" className="block text-sm font-medium text-gray-700 mb-2">
              Summary *
            </Label>
            <Textarea
              id="overview"
              value={formData.overview}
              onChange={(e) => handleInputChange('overview', e.target.value)}
              className="w-full rounded-lg border-gray-300"
              rows={3}
              placeholder="Enter evidence summary..."
              required
            />
            <p className="text-xs text-muted-foreground mt-1">
              Minimum 10 characters required
            </p>
          </div>
        </div>

        {/* Detailed Description White Box */}
        <div className="bg-white p-6 rounded-lg border border-gray-200 shadow-sm">
          <div className="mb-4">
            <Label className="text-lg font-semibold">
              Detailed Description
            </Label>
          </div>
          
          <div>
            <Label htmlFor="detailedDescription" className="block text-sm font-medium text-gray-700 mb-2">
              Detailed Description *
            </Label>
            <Textarea
              id="detailedDescription"
              value={formData.detailedDescription}
              onChange={(e) => handleInputChange('detailedDescription', e.target.value)}
              className="w-full rounded-lg border-gray-300"
              rows={8}
              placeholder="Enter detailed description of the evidence..."
              required
            />
            <p className="text-xs text-muted-foreground mt-1">
              Minimum 20 characters required
            </p>
          </div>
        </div>

        {/* Initial Condition White Box */}
        <div className="bg-white p-6 rounded-lg border border-gray-200 shadow-sm">
          <div className="mb-4">
            <Label className="text-lg font-semibold">
              Initial Condition
            </Label>
          </div>
          
          <div>
            <Label htmlFor="initialCondition" className="block text-sm font-medium text-gray-700 mb-2">
              Initial Condition Assessment
            </Label>
            <Textarea
              id="initialCondition"
              value={formData.initialCondition}
              onChange={(e) => handleInputChange('initialCondition', e.target.value)}
              className="w-full rounded-lg border-gray-300"
              rows={6}
              placeholder="Enter initial condition of the evidence..."
            />
          </div>
        </div>

        {/* Initial Preservation Measures White Box */}
        <div className="bg-white p-6 rounded-lg border border-gray-200 shadow-sm">
          <div className="mb-4">
            <Label className="text-lg font-semibold">
              Initial Preservation Measures
            </Label>
          </div>
          
          <div>
            <Label htmlFor="preservationMeasures" className="block text-sm font-medium text-gray-700 mb-2">
              Preservation Measures Taken
            </Label>
            <Textarea
              id="preservationMeasures"
              value={formData.preservationMeasures}
              onChange={(e) => handleInputChange('preservationMeasures', e.target.value)}
              className="w-full rounded-lg border-gray-300"
              rows={6}
              placeholder="Enter preservation measures taken..."
            />
          </div>
        </div>

        {/* Action Buttons */}
        <div className="flex justify-end gap-4 bg-white p-4 rounded-lg">
          <Button type="button" variant="outline" onClick={handleClearAll} className="rounded-full">
            Clear All
          </Button>
          <Button type="submit" disabled={isLoading} className="rounded-full">
            {isLoading ? "Saving..." : "Save Evidence"}
          </Button>
          <Button type="button" variant="outline" onClick={handleCancelForm} className="rounded-full">
            Cancel
          </Button>
        </div>
      </form>
    </div>
  );
}