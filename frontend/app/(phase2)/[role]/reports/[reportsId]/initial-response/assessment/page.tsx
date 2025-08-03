"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { ArrowLeft } from "lucide-react";
import { useRouter, useParams } from "next/navigation";
import { AssessmentForm, AssessmentFormData } from "@/components/form/AssessmentForm";

/**
 * ASSESSMENT DETAIL PAGE
 * 
 * This page displays detailed information about a specific preliminary assessment.
 * It shows comprehensive details including overview, description, condition, and preservation measures.
 * 
 * FEATURES:
 * - Overview section with key information
 * - Detailed description in editable text area
 * - Initial condition assessment
 * - Initial preservation measures taken
 * - Navigation back to main assessment list
 * - Delete functionality with confirmation modal
 */

export default function AssessmentDetailPage() {
   const router = useRouter();
   const params = useParams();
   const [isEditing, setIsEditing] = useState(false);

   const handleBack = () => {
      // Navigate back to initial response page with correct dynamic parameters
      router.push(`/${params.role}/reports/${params.reportsId}/initial-response`);
   };

   const handleEdit = () => {
      setIsEditing(!isEditing);
   };

   const handleSave = (data: AssessmentFormData) => {
      // This is now handled by the form component itself
   };

   const handleCancel = () => {
      setIsEditing(false);
   };

   const handleDelete = () => {
      // This is now handled by the form component itself
      // Navigate back to main assessment list after deletion with correct dynamic parameters
      router.push(`/${params.role}/reports/${params.reportsId}/initial-response`);
   };

   return (
      <main className="flex-1 p-6">
         {/* Header with Back Button */}
         <div className="mb-6">
            <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
               PRELIMINARY ASSESSMENT DETAILS
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
                  Back to Assessments
               </Button>
            </div>

            {/* Assessment Form */}
            <AssessmentForm
               isEditing={isEditing}
               onEdit={handleEdit}
               onCancel={handleCancel}
            />
         </div>
      </main>
   );
}