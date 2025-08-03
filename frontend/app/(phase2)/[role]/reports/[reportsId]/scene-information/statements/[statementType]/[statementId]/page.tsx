"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { ArrowLeft, Trash2 } from "lucide-react";
import { useRouter, useParams } from "next/navigation";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { StatementForm, StatementFormData } from "@/components/form/StatementForm";
import { toast } from "sonner";

/**
 * STATEMENT DETAIL PAGE
 * 
 * Route structure: /statements/[statementType]/[statementId]
 * - statementType: witness | victim | relevant-party
 * - statementId: unique identifier for the statement
 */

export default function StatementDetailPage() {
   const router = useRouter();
   const params = useParams();
   const [isEditing, setIsEditing] = useState(false);
   const [showDeleteModal, setShowDeleteModal] = useState(false);
   const [statementData, setStatementData] = useState<StatementFormData | null>(null);

   // Get statement type from route params (much cleaner!)
   const statementType = params.statementType as "witness" | "victim" | "relevant-party";
   const statementId = params.statementId as string;
   
   const titles = {
      witness: "WITNESS STATEMENT DETAILS",
      victim: "VICTIM STATEMENT DETAILS", 
      "relevant-party": "RELEVANT PARTY STATEMENT DETAILS",
   };

   // Load data on component mount based on route params
   useState(() => {
      const storageKey = `${statementType}Statements`;
      const stored = JSON.parse(sessionStorage.getItem(storageKey) || "[]");
      const found = stored.find((item: any) => item.id === statementId);
      
      if (found) {
         setStatementData(found);
      } else {
         // New statement - create empty data structure
         setStatementData({
            id: statementId,
            personName: "",
            date: "",
            phoneNumber: "",
            role: "",
            detailedStatement: "",
            formType: statementType,
            files: [],
         });
         setIsEditing(true); // Start in edit mode for new statements
      }
   });

   const handleBack = () => {
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information`);
   };

   const handleEdit = () => {
      setIsEditing(true);
   };

   const handleFormSubmit = (data: StatementFormData) => {
      // Save to sessionStorage
      const storageKey = `${statementType}Statements`;
      const stored = JSON.parse(sessionStorage.getItem(storageKey) || "[]");
      
      const existingIndex = stored.findIndex((item: any) => item.id === data.id);
      if (existingIndex >= 0) {
         stored[existingIndex] = data;
      } else {
         stored.push(data);
      }
      
      sessionStorage.setItem(storageKey, JSON.stringify(stored));
      
      setStatementData(data);
      setIsEditing(false);
      toast.success(`${statementType.charAt(0).toUpperCase() + statementType.slice(1)} statement saved successfully!`);
   };

   const handleFormCancel = () => {
      setIsEditing(false);
   };

   const hasContent = () => {
      return statementData && (
         statementData.personName.trim() !== "" ||
         statementData.detailedStatement.trim() !== "" ||
         statementData.files.length > 0
      );
   };

   const handleDeleteClick = () => {
      if (!hasContent()) {
         toast.error("No statement data to delete");
         return;
      }
      setShowDeleteModal(true);
   };

   const handleConfirmDelete = () => {
      if (statementData) {
         const storageKey = `${statementType}Statements`;
         const stored = JSON.parse(sessionStorage.getItem(storageKey) || "[]");
         const updated = stored.filter((item: any) => item.id !== statementData.id);
         sessionStorage.setItem(storageKey, JSON.stringify(updated));
         
         toast.success(`${statementType.charAt(0).toUpperCase() + statementType.slice(1)} statement deleted successfully!`);
         router.push(`/${params.role}/reports/${params.reportsId}/scene-information`);
      }
      setShowDeleteModal(false);
   };

   const handleCloseModal = () => {
      setShowDeleteModal(false);
   };

   if (!statementData) {
      return <div>Loading...</div>;
   }

   return (
      <main className="flex-1 p-6">
         {/* Header */}
         <div className="mb-6">
            <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
               {titles[statementType]} {statementData.personName && `- ${statementData.personName}`}
            </h1>
         </div>

         {/* Main Content Container */}
         <div className="bg-gray-300 rounded-b-lg shadow p-6 space-y-6">
            {/* Statement Form */}
            <StatementForm
               initialData={statementData}
               onSubmit={handleFormSubmit}
               onCancel={handleFormCancel}
               isLoading={false}
               isEditing={isEditing}
               onEdit={handleEdit}
               formType={statementType}
            />

            {/* ACTION BUTTONS */}
            <div className="flex justify-between items-center bg-white p-4 rounded-lg">
               <Button
                  variant="outline"
                  onClick={handleBack}
                  className="flex items-center gap-2 rounded-full"
               >
                  <ArrowLeft className="w-4 h-4" />
                  Back to Scene Information
               </Button>

               <Button
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

         <DeleteEvidenceModal
            isOpen={showDeleteModal}
            onClose={handleCloseModal}
            onConfirm={handleConfirmDelete}
            evidenceName={statementData.personName ? `${statementType.charAt(0).toUpperCase() + statementType.slice(1)} Statement - ${statementData.personName}` : `this ${statementType} statement`}
         />
      </main>
   );
}