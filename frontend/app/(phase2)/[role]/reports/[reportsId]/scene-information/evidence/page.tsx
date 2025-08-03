"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { ArrowLeft, Trash2 } from "lucide-react";
import { useRouter } from "next/navigation";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { EvidenceForm } from "@/components/form/EvidenceForm";
import { toast } from "sonner";

/**
 * EVIDENCE DETAIL PAGE
 * 
 * This page displays detailed information about a specific piece of evidence.
 * It shows comprehensive details including overview, description, condition, and preservation measures.
 * This is a blank, editable version for creating new evidence records.
 * 
 * FEATURES:
 * - Overview section with key information (editable)
 * - Detailed description in editable text area
 * - Initial condition assessment (editable)
 * - Initial preservation measures taken (editable)
 * - Navigation back to main evidence list
 * - Delete functionality with confirmation modal
 * - All fields editable by default
 */

export default function EvidenceDetailPage() {
   const router = useRouter();
   const [showDeleteModal, setShowDeleteModal] = useState(false);
   const [evidenceData, setEvidenceData] = useState<any>(null);

   const handleBack = () => {
      router.back(); // Navigate back to previous page
   };

   const handleFormSubmit = (data: any) => {
      setEvidenceData(data);
      toast.success("Evidence saved successfully!");
   };

   const handleFormCancel = () => {
      router.back();
   };

   // Delete handlers for modal
   const handleDeleteClick = () => {
      if (!evidenceData?.id) {
         toast.error("No evidence data to delete");
         return;
      }
      setShowDeleteModal(true);
   };

   const handleConfirmDelete = () => {
      if (evidenceData?.id) {
         // Remove from sessionStorage
         const evidenceRecords = JSON.parse(
            sessionStorage.getItem("evidenceRecords") || "[]"
         );
         const updatedRecords = evidenceRecords.filter(
            (item: any) => item.id !== evidenceData.id
         );
         sessionStorage.setItem("evidenceRecords", JSON.stringify(updatedRecords));

         toast.success("Evidence deleted successfully!");

         // Navigate back to main evidence list after deletion
         router.push(`${window.location.pathname.split('/evidence')[0]}`);
      }

      setShowDeleteModal(false);
   };

   const handleCloseModal = () => {
      setShowDeleteModal(false);
   };

   return (
      <main className="flex-1 p-6">
         {/* Header */}
         <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
            EVIDENCE DETAILS {evidenceData?.id && `- ${evidenceData.id}`}
         </h1>


         {/* Main Content Container */}
         <div className="bg-gray-300 rounded-b-lg shadow p-6 space-y-6">
            {/* Evidence Form */}
            <EvidenceForm
               onSubmit={handleFormSubmit}
               onCancel={handleFormCancel}
               isLoading={false}
            />

            {/* Navigation and Delete Buttons */}
            <div className="flex justify-between items-center bg-white p-4 rounded-lg">
               {/* Back Button - Left Side */}
               <Button
                  variant="outline"
                  onClick={handleBack}
                  className="flex items-center gap-2 rounded-full"
               >
                  <ArrowLeft className="w-4 h-4" />
                  Back to Evidence List
               </Button>

               {/* Delete Button - Right Side */}
               <Button
                  variant="destructive"
                  onClick={handleDeleteClick}
                  className="flex items-center gap-2 rounded-full"
                  disabled={!evidenceData?.id}
               >
                  <Trash2 className="w-4 h-4" />
                  Delete
               </Button>
            </div>
         </div>

         {/* DELETE CONFIRMATION MODAL */}
         <DeleteEvidenceModal
            isOpen={showDeleteModal}
            onClose={handleCloseModal}
            onConfirm={handleConfirmDelete}
            evidenceName={evidenceData?.id ? `Evidence ${evidenceData.id} - ${evidenceData.location}` : "this evidence"}
         />
      </main>
   );
}