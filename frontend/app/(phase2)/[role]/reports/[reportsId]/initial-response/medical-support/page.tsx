"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { ArrowLeft } from "lucide-react";
import { useRouter, useParams } from "next/navigation";
import { MedicalSupportForm, MedicalSupportFormData } from "@/components/form/MedicalSupportForm";
import { UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";

/**
 * MEDICAL/RESCUE SUPPORT PAGE
 * 
 * This page allows for creating and editing medical/rescue support information.
 * It provides comprehensive forms for capturing support details and documentation.
 * 
 * FEATURES:
 * - Medical/rescue unit information
 * - Support type and personnel details
 * - Location and assignment tracking
 * - Remarks and notes section
 * - Scene sketch upload functionality with image preview
 * - Edit toggle functionality like assessment page
 * - Blank field validation
 */

export default function MedicalSupportPage() {
   const router = useRouter();
   const params = useParams();
   const [isEditing, setIsEditing] = useState(false);

   const handleBack = () => {
      // Navigate back to initial response page
      router.push(`/${params.role}/reports/${params.reportsId}/initial-response`);
   };

   const handleEdit = () => {
      setIsEditing(!isEditing);
   };

   const handleSave = (data: MedicalSupportFormData, files: UploadedFile[]) => {
      // TODO: Save medical support data to database/API
      console.log("Saving medical support data:", data);
      console.log("Uploaded files:", files);
      setIsEditing(false);
   };

   const handleCancel = () => {
      setIsEditing(false);
   };

   const handleDelete = () => {
      console.log("Deleting medical support record");
      // Navigate back to initial response page after deletion
      router.push(`/${params.role}/reports/${params.reportsId}/initial-response`);
   };

   const handleClearAll = () => {
      // This will be handled by the form component
   };

   return (
      <main className="flex-1 p-6">
         {/* Header */}
         <div className="mb-6">
            <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
               MEDICAL/RESCUE SUPPORT
            </h1>
         </div>

         {/* Main Content Container */}
         <div className="bg-gray-300 rounded-b-lg shadow p-6">
            {/* Back Button - Above Form */}
            <div className="mb-6">
               <Button
                  variant="outline"
                  onClick={handleBack}
                  className="flex items-center gap-2 rounded-full"
               >
                  <ArrowLeft className="w-4 h-4" />
                  Back to Initial Response
               </Button>
            </div>

            {/* Medical Support Form */}
            <MedicalSupportForm
               isEditing={isEditing}
               onEdit={handleEdit}
               onCancel={handleCancel}
            />
         </div>
      </main>
   );
}