"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { ArrowLeft, Trash2, Calendar, Clock } from "lucide-react";
import { useRouter, useParams } from "next/navigation";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { FileUploadWithPreview, UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";
import { toast } from "sonner";

/**
 * IMAGES AND VIDEOS PAGE
 * 
 * This page allows for creating and editing media documentation from the scene.
 * It provides comprehensive forms for capturing media details and metadata.
 * 
 * FEATURES:
 * - Date and time capture for when media was taken
 * - Media file upload with preview capabilities
 * - Description of the media content
 * - Information about who captured the media
 * - Edit toggle functionality
 * - Blank field validation
 */

// Form data interface for type safety
interface MediaFormData {
   id?: string;
   dateTaken: string;
   timeTaken: string;
   isAM: boolean;
   description: string;
   capturedBy: string;
   files: UploadedFile[];
   createdAt?: string;
   updatedAt?: string;
}

// Mock data for the media record
const initialMediaData: MediaFormData = {
   dateTaken: "2024-07-03",
   timeTaken: "14:45",
   isAM: false, // PM
   description: "Photos and video footage of the accident scene showing vehicle positions, damage assessment, skid marks on the pavement, traffic signals, and surrounding area. Video includes 360-degree view of the intersection and close-up shots of vehicle damage. Photos document evidence placement and witness positions.",
   capturedBy: "Officer Sarah Martinez - Badge #4521",
   files: []
};

export default function ImagesAndVideosPage() {
   const router = useRouter();
   const params = useParams();
   const [isEditing, setIsEditing] = useState(false);
   const [formData, setFormData] = useState<MediaFormData>(initialMediaData);
   const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>([]);
   
   // State for controlling the delete modal
   const [showDeleteModal, setShowDeleteModal] = useState(false);

   // Form validation
   const validateForm = (): string[] => {
      const errors: string[] = [];
      
      if (!formData.dateTaken.trim()) {
         errors.push("Date taken is required.");
      }
      if (!formData.timeTaken.trim()) {
         errors.push("Time taken is required.");
      }
      if (!formData.description.trim() || formData.description.length < 10) {
         errors.push("Description must be at least 10 characters.");
      }
      if (!formData.capturedBy.trim()) {
         errors.push("Captured by information is required.");
      }
      if (uploadedFiles.length === 0) {
         errors.push("At least one media file is required.");
      }
      
      return errors;
   };

   // Check if data has content (not all blank)
   const hasContent = (): boolean => {
      return formData.dateTaken.trim() !== "" ||
             formData.timeTaken.trim() !== "" ||
             formData.description.trim() !== "" ||
             formData.capturedBy.trim() !== "" ||
             uploadedFiles.length > 0;
   };

   const handleBack = () => {
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information`);
   };

   const handleEdit = () => {
      setIsEditing(!isEditing);
   };

   // Form submission handler
   const handleSubmit = (e: React.FormEvent) => {
      e.preventDefault();
      
      const errors = validateForm();
      if (errors.length > 0) {
         toast.error("Please fix the following errors:", {
            description: errors.join("\n"),
         });
         return;
      }

      // Prepare complete form data
      const completeFormData: MediaFormData = {
         ...formData,
         id: formData.id || `media-${Date.now()}`,
         files: uploadedFiles,
         createdAt: formData.createdAt || new Date().toISOString(),
         updatedAt: new Date().toISOString(),
      };

      // Save to sessionStorage
      const mediaRecords = JSON.parse(sessionStorage.getItem("mediaRecords") || "[]");
      const existingIndex = mediaRecords.findIndex((item: any) => item.id === completeFormData.id);
      
      if (existingIndex >= 0) {
         mediaRecords[existingIndex] = completeFormData;
      } else {
         mediaRecords.push(completeFormData);
      }
      
      sessionStorage.setItem("mediaRecords", JSON.stringify(mediaRecords));

      toast.success("Media record saved successfully!");
      setIsEditing(false);
      
      console.log("Saved media data:", completeFormData);
   };

   const handleCancel = () => {
      // Reset to original data and clean up preview URLs
      uploadedFiles.forEach(uploadedFile => {
         if (uploadedFile.preview) {
            URL.revokeObjectURL(uploadedFile.preview);
         }
      });
      setFormData(initialMediaData);
      setUploadedFiles([]);
      setIsEditing(false);
   };

   const handleClearAll = () => {
      // Clear all data when in edit mode and clean up preview URLs
      uploadedFiles.forEach(uploadedFile => {
         if (uploadedFile.preview) {
            URL.revokeObjectURL(uploadedFile.preview);
         }
      });
      setFormData({
         dateTaken: "",
         timeTaken: "",
         isAM: true,
         description: "",
         capturedBy: "",
         files: []
      });
      setUploadedFiles([]);
   };

   const handleInputChange = (field: keyof MediaFormData, value: string | boolean) => {
      setFormData(prev => ({
         ...prev,
         [field]: value
      }));
   };

   // Delete handlers for modal
   const handleDeleteClick = () => {
      if (!hasContent()) {
         toast.error("No media data to delete");
         return;
      }
      setShowDeleteModal(true);
   };

   const handleConfirmDelete = () => {
      if (formData.id) {
         // Remove from sessionStorage
         const mediaRecords = JSON.parse(sessionStorage.getItem("mediaRecords") || "[]");
         const updatedRecords = mediaRecords.filter((item: any) => item.id !== formData.id);
         sessionStorage.setItem("mediaRecords", JSON.stringify(updatedRecords));
      }
      
      // Clean up preview URLs
      uploadedFiles.forEach(uploadedFile => {
         if (uploadedFile.preview) {
            URL.revokeObjectURL(uploadedFile.preview);
         }
      });
      
      toast.success("Media record deleted successfully!");
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information`);
      setShowDeleteModal(false);
   };

   const handleCloseModal = () => {
      setShowDeleteModal(false);
   };

   const handleFilesChange = (files: UploadedFile[]) => {
      setUploadedFiles(files);
   };

   // Time toggle component
   const TimeToggle = ({ isAM, setIsAM, disabled = false }: { isAM: boolean, setIsAM: (value: boolean) => void, disabled?: boolean }) => (
      <div className="flex items-center justify-center bg-gray-200 rounded-full w-24 h-8 px-1 relative">
         <button
            type="button"
            disabled={disabled}
            className={`flex-1 h-6 rounded-full text-sm font-medium focus:outline-none transition-colors duration-200 ${
               isAM 
                  ? 'bg-white text-blue-900 shadow' 
                  : disabled 
                     ? 'text-gray-400' 
                     : 'text-gray-500 hover:text-gray-700'
            }`}
            style={{ marginRight: "2px" }}
            onClick={() => !disabled && setIsAM(true)}
         >
            AM
         </button>
         <button
            type="button"
            disabled={disabled}
            className={`flex-1 h-6 rounded-full text-sm font-medium focus:outline-none transition-colors duration-200 ${
               !isAM 
                  ? 'bg-white text-blue-900 shadow' 
                  : disabled 
                     ? 'text-gray-400' 
                     : 'text-gray-500 hover:text-gray-700'
            }`}
            onClick={() => !disabled && setIsAM(false)}
         >
            PM
         </button>
      </div>
   );

   return (
      <main className="flex-1 p-6">
         {/* Header */}
         <div className="mb-6">
            <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
               IMAGES AND VIDEOS
            </h1>
         </div>

         {/* Main Content Container */}
         <div className="bg-gray-300 rounded-b-lg shadow p-6 space-y-6">
            {/* Wrap everything in a form */}
            <form onSubmit={handleSubmit} className="space-y-6">
               
               {/* DATE AND TIME TAKEN SECTION */}
               <div className="bg-white rounded-lg p-6">
                  <label className="block font-semibold text-lg mb-4">
                     DATE AND TIME TAKEN
                  </label>
                  <div className="grid grid-cols-2 gap-4">
                     <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Date *</label>
                        <div className="flex items-center gap-2">
                           {isEditing ? (
                              <>
                                 <Calendar className="w-4 h-4 text-gray-500" />
                                 <Input
                                    type="date"
                                    value={formData.dateTaken}
                                    onChange={(e) => handleInputChange('dateTaken', e.target.value)}
                                    className="flex-1 rounded-lg"
                                    required
                                 />
                              </>
                           ) : (
                              <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center gap-2">
                                 <Calendar className="w-4 h-4 text-gray-500" />
                                 {formData.dateTaken ? 
                                    new Date(formData.dateTaken).toLocaleDateString('en-US', {
                                       weekday: 'long',
                                       year: 'numeric',
                                       month: 'long',
                                       day: 'numeric'
                                    }) : 
                                    <span className="text-gray-500 italic">No date specified</span>
                                 }
                              </div>
                           )}
                        </div>
                     </div>
                     
                     <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Time *</label>
                        <div className="flex items-center gap-2">
                           {isEditing ? (
                              <>
                                 <Clock className="w-4 h-4 text-gray-500" />
                                 <Input 
                                    className="w-20 rounded-full" 
                                    value={formData.timeTaken}
                                    onChange={(e) => handleInputChange('timeTaken', e.target.value)}
                                    placeholder="HH:MM"
                                    pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"
                                    required
                                 />
                                 <TimeToggle 
                                    isAM={formData.isAM} 
                                    setIsAM={(value) => handleInputChange('isAM', value)} 
                                 />
                              </>
                           ) : (
                              <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center gap-2">
                                 <Clock className="w-4 h-4 text-gray-500" />
                                 {formData.timeTaken ? 
                                    `${formData.timeTaken} ${formData.isAM ? 'AM' : 'PM'}` : 
                                    <span className="text-gray-500 italic">No time specified</span>
                                 }
                              </div>
                           )}
                        </div>
                     </div>
                  </div>
               </div>

               {/* MEDIA FILES SECTION - Using the reusable component */}
               <FileUploadWithPreview
                  uploadedFiles={uploadedFiles}
                  onFilesChange={handleFilesChange}
                  isEditing={isEditing}
                  title="MEDIA FILES (IMAGES & VIDEOS)"
                  description="Supports: JPG, PNG, MP4, MOV, AVI, GIF files"
                  accept=".jpg,.jpeg,.png,.mp4,.mov,.avi,.gif,.webm,.mkv"
                  maxFiles={50}
               />

               {/* DESCRIPTION SECTION */}
               <div className="bg-white rounded-lg p-6">
                  <label className="block font-semibold text-lg mb-4">
                     DESCRIPTION *
                  </label>
                  {isEditing ? (
                     <Textarea
                        value={formData.description}
                        onChange={(e) => handleInputChange('description', e.target.value)}
                        placeholder="Enter description of the images and videos..."
                        className="w-full rounded-lg border-gray-300"
                        rows={6}
                        required
                        minLength={10}
                     />
                  ) : (
                     <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[8rem] whitespace-pre-line">
                        {formData.description || <span className="text-gray-500 italic">No description provided</span>}
                     </div>
                  )}
               </div>

               {/* CAPTURED BY SECTION */}
               <div className="bg-white rounded-lg p-6">
                  <label className="block font-semibold text-lg mb-4">
                     CAPTURED BY *
                  </label>
                  {isEditing ? (
                     <Input
                        value={formData.capturedBy}
                        onChange={(e) => handleInputChange('capturedBy', e.target.value)}
                        placeholder="Enter who captured the media (officer name, badge number, etc.)..."
                        className="w-full rounded-lg"
                        required
                     />
                  ) : (
                     <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                        {formData.capturedBy || <span className="text-gray-500 italic">No capture information specified</span>}
                     </div>
                  )}
               </div>

               {/* ACTION BUTTONS */}
               <div className="flex justify-between items-center bg-white p-4 rounded-lg">
                  {/* Back Button - Left Side */}
                  <Button
                     type="button"
                     variant="outline"
                     onClick={handleBack}
                     className="flex items-center gap-2 rounded-full"
                  >
                     <ArrowLeft className="w-4 h-4" />
                     Back to Scene Information
                  </Button>

                  {/* Edit/Save/Cancel and Delete Buttons - Right Side */}
                  <div className="flex gap-4">
                     {isEditing ? (
                        <>
                           <Button type="button" variant="outline" onClick={handleCancel} className="rounded-full">
                              Cancel
                           </Button>
                           <Button type="button" variant="outline" onClick={handleClearAll} className="rounded-full text-red-600 hover:text-red-700">
                              Clear All
                           </Button>
                           <Button type="submit" className="rounded-full">
                              Save Media
                           </Button>
                        </>
                     ) : (
                        <Button type="button" onClick={handleEdit} className="rounded-full">
                           Edit Media
                        </Button>
                     )}

                     <Button
                        type="button"
                        variant="destructive"
                        onClick={handleDeleteClick}
                        className="flex items-center gap-2 rounded-full"
                        disabled={!hasContent()}
                     >
                        <Trash2 className="w-4 h-4" />
                        Delete
                     </Button>
                  </div>
               </div>
            </form>
         </div>

         {/* DELETE CONFIRMATION MODAL */}
         <DeleteEvidenceModal
            isOpen={showDeleteModal}
            onClose={handleCloseModal}
            onConfirm={handleConfirmDelete}
            evidenceName="this media record"
         />
      </main>
   );
}

// Export the interface for potential reuse
export type { MediaFormData };