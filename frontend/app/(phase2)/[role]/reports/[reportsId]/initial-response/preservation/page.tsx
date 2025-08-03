"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { ArrowLeft } from "lucide-react";
import { useRouter, useParams } from "next/navigation";
import { ScenePreservationForm, ScenePreservationFormData } from "@/components/form/ScenePreservationForm";
import { UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";

/**
 * SCENE PRESERVATION MEASURES PAGE
 * 
 * This page allows for creating and editing scene preservation measures information.
 * It provides comprehensive forms for capturing preservation details and documentation.
 * 
 * FEATURES:
 * - Responsible unit/officer information
 * - Arrival time tracking
 * - Start and end time tracking with AM/PM toggles
 * - Scene preservation methods description
 * - Area covered/perimeter details
 * - Notes and special instructions
 * - Attachment upload functionality with preview
 * - Edit toggle functionality
 * - Blank field validation
 */

export default function ScenePreservationPage() {
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

   const handleSave = (data: ScenePreservationFormData, files: UploadedFile[]) => {
      // TODO: Save preservation data to database/API
      console.log("Saving preservation data:", data);
      console.log("Uploaded files:", files);
      setIsEditing(false);
   };

   const handleCancel = () => {
      setIsEditing(false);
   };

   const handleDelete = () => {
      console.log("Deleting preservation measures");
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
               SCENE PRESERVATION MEASURES
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

            {/* Scene Preservation Form */}
            <ScenePreservationForm
               isEditing={isEditing}
               onEdit={handleEdit}
               onCancel={handleCancel}
            />
         </div>
      </main>
   );
}
