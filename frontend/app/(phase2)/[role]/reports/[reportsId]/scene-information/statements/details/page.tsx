"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { ArrowLeft, Trash2 } from "lucide-react";
import { useRouter, useParams, useSearchParams } from "next/navigation";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { StatementForm } from "@/components/form/StatementForm";
import { UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";
import { toast } from "sonner";

/**
 * STATEMENT DETAIL PAGE
 * 
 * This page displays detailed information about statements from witnesses, victims, or other relevant parties.
 * It shows comprehensive details including person information, statement content, and evidence files.
 * Features edit toggle functionality similar to other detail pages.
 * 
 * FEATURES:
 * - Person information form (name, date, contact, role)
 * - Detailed statement recording
 * - Evidence file upload and management with preview
 * - Edit toggle functionality
 * - Navigation back to statements list
 * - Delete functionality with confirmation modal
 * - Support for different statement types (witness, victim, relevant-party)
 * 
 * Currently, this page uses mock data for demonstration purposes.
 * The real page is over at /statements/[statementType]/[statementId]
 */

// Mock data structure for existing statement
const statementData = {
   personName: "Sarah Johnson",
   date: "2024-07-03",
   phoneNumber: "+1-555-0123",
   role: "Witness",
   detailedStatement: "I was walking down Main Street around 2:30 PM when I heard a loud crash. I turned around and saw two vehicles had collided at the intersection. The blue sedan ran the red light and hit the white SUV that was making a left turn. I immediately called 911 and stayed at the scene to provide assistance. The driver of the blue sedan appeared to be on their phone at the time of impact."
};

export default function StatementDetailPage() {
   const router = useRouter();
   const params = useParams();
   const searchParams = useSearchParams();
   const [isEditing, setIsEditing] = useState(false);
   const [showDeleteModal, setShowDeleteModal] = useState(false);
   const [currentStatementData, setCurrentStatementData] = useState(statementData);
   const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>([]);

   // Determine statement type from URL or search params
   const statementType = searchParams.get("type") as "witness" | "victim" | "relevant-party" || "witness";
   
   const titles = {
      witness: "WITNESS STATEMENT DETAILS",
      victim: "VICTIM STATEMENT DETAILS", 
      "relevant-party": "RELEVANT PARTY STATEMENT DETAILS",
   };

   // Check if data has content (not all blank)
   const hasContent = () => {
      return currentStatementData.personName.trim() !== "" ||
             currentStatementData.date.trim() !== "" ||
             currentStatementData.phoneNumber.trim() !== "" ||
             currentStatementData.role.trim() !== "" ||
             currentStatementData.detailedStatement.trim() !== "" ||
             uploadedFiles.length > 0;
   };

   const handleBack = () => {
      // Navigate back to scene information page with correct dynamic parameters
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information`);
   };

   const handleEdit = () => {
      setIsEditing(true);
   };

   const handleFormSubmit = (data: any) => {
      setCurrentStatementData(data);
      // If files are included in data, update uploadedFiles accordingly
      if (data.uploadedFiles) {
         setUploadedFiles(data.uploadedFiles);
      }
      setIsEditing(false);
      toast.success(`${statementType.charAt(0).toUpperCase() + statementType.slice(1)} statement saved successfully!`);
   };

   const handleFormCancel = () => {
      setIsEditing(false);
   };

   // Delete handlers for modal
   const handleDeleteClick = () => {
      if (!hasContent()) {
         toast.error("No statement data to delete");
         return;
      }
      setShowDeleteModal(true);
   };

   const handleConfirmDelete = () => {
      // Clean up preview URLs
      uploadedFiles.forEach(uploadedFile => {
         if (uploadedFile.preview) {
            URL.revokeObjectURL(uploadedFile.preview);
         }
      });

      // Remove from sessionStorage
      const statements = JSON.parse(
         sessionStorage.getItem(`${statementType}Statements`) || "[]"
      );
      const updatedStatements = statements.filter(
         (item: any) => item.personName !== currentStatementData.personName
      );
      sessionStorage.setItem(`${statementType}Statements`, JSON.stringify(updatedStatements));
      
      toast.success(`${statementType.charAt(0).toUpperCase() + statementType.slice(1)} statement deleted successfully!`);
      
      // Navigate back to scene information page after deletion
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information`);
      
      setShowDeleteModal(false);
   };

   const handleCloseModal = () => {
      setShowDeleteModal(false);
   };

   return (
      <main className="flex-1 p-6">
         {/* Header */}
         <div className="mb-6">
            <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
               {titles[statementType]} {currentStatementData.personName && `- ${currentStatementData.personName}`}
            </h1>
         </div>

         {/* Main Content Container */}
         <div className="bg-gray-300 rounded-b-lg shadow p-6 space-y-6">
            {/* Statement Form */}
            <StatementForm
               initialData={currentStatementData}
               onSubmit={handleFormSubmit}
               onCancel={handleFormCancel}
               isLoading={false}
               isEditing={isEditing}
               onEdit={handleEdit}
               formType={statementType}
            />

            {/* ACTION BUTTONS */}
            <div className="flex justify-between items-center bg-white p-4 rounded-lg">
               {/* Back Button - Left Side */}
               <Button
                  variant="outline"
                  onClick={handleBack}
                  className="flex items-center gap-2 rounded-full"
               >
                  <ArrowLeft className="w-4 h-4" />
                  Back to Scene Information
               </Button>

               {/* Delete Button - Right Side */}
               <Button
                  variant="destructive"
                  onClick={handleDeleteClick}
                  className="flex items-center gap-2 rounded-full"
                  disabled={!hasContent()} // Disable if no content
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
            evidenceName={currentStatementData.personName ? `${statementType.charAt(0).toUpperCase() + statementType.slice(1)} Statement - ${currentStatementData.personName}` : `this ${statementType} statement`}
         />
      </main>
   );
}