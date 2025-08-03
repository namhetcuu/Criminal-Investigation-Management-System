"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { useAppDispatch, useAppSelector } from "@/store/phase2/hooks";
import { fetchFieldReport, saveFieldReport } from "@/store/phase2/slices/fieldReportSlice";
import { FieldReportForm } from "@/components/form/FieldReportForm";
import { useToast } from "@/components/ui/use-toast";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { SubmitSuccessModal } from "@/components/features/phase2/SubmitSuccessModal";
import { SectionContainer } from "@/components/features/phase2/SectionContainer";

/**
 * FIELD REPORT PAGE
 * 
 * This page provides a comprehensive overview of the field report with quick access
 * to different sections and detailed information entry capabilities.
 * 
 * FEATURES:
 * - Initial Response overview with details button
 * - Scene Information overview with details button
 * - Report details text input
 * - Images and videos table display
 * - Level assessment with urgency dropdown and description
 * - Edit toggle functionality
 * - Navigation to related pages
 */

export default function FieldReportPage() {
   const params = useParams();
   const router = useRouter();
   const dispatch = useAppDispatch();
   const { toast } = useToast();

   const { data: fieldReport, isLoading, isSaving, error } = useAppSelector(
      (state) => state.fieldReport
   );

   const [isEditing, setIsEditing] = useState(false);
   const [showDeleteModal, setShowDeleteModal] = useState(false);
   const [showSuccessModal, setShowSuccessModal] = useState(false);

   const reportId = params.reportsId as string;
   const role = params.role as string;

   useEffect(() => {
      if (reportId) {
         dispatch(fetchFieldReport(reportId));
      }
   }, [dispatch, reportId]);

   useEffect(() => {
      if (error) {
         toast({
            title: "Error",
            description: error,
            variant: "destructive",
         });
      }
   }, [error, toast]);

   const handleFormSubmit = async (data: any) => {
      try {
         await dispatch(saveFieldReport({ reportId, data })).unwrap();
         setIsEditing(false);
         setShowSuccessModal(true);
         toast({
            title: "Success",
            description: "Field report saved successfully",
         });
      } catch (error) {
         // Error handled by Redux and useEffect
      }
   };

   const handleBack = () => {
      router.push(`/${role}/reports/${reportId}`);
   };

   const handleDelete = () => {
      setShowDeleteModal(true);
   };

   const handleConfirmDelete = async () => {
      // TODO: Implement delete API call
      router.push(`/${role}/reports/${reportId}`);
      setShowDeleteModal(false);
   };

   if (isLoading) {
      return (
         <div className="flex-1 p-6">
            <div className="animate-pulse space-y-4">
               <div className="h-8 bg-gray-200 rounded w-1/4"></div>
               <div className="h-64 bg-gray-200 rounded"></div>
            </div>
         </div>
      );
   }

   function handleViewStatement(): void {
      router.push(`/${role}/reports/${reportId}/initial-response`);
   }

   function handleViewScene(): void {
      router.push(`/${role}/reports/${reportId}/scene-information`);
   }

   return (
      <main className="flex-1 p-6">
         {/* Header */}
         <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
            FIELD REPORT - CASE #{reportId}
         </h1>

         <div className="bg-gray-300 rounded-b-lg shadow p-6 pt-10 space-y-6">
            <SectionContainer
               label="INITIAL RESPONSE"
               onAdd={handleViewStatement}
               addButtonText="Detail"
               icon={false} // No icon for this button
            > <span></span>
            </SectionContainer>

            <SectionContainer
               label="SCENE INFORMATION"
               onAdd={handleViewScene}
               addButtonText="Detail"
               icon={false} // No icon for this button
            > <span></span>
            </SectionContainer>

            {/* Field Report Form */}
            <FieldReportForm
               initialData={fieldReport}
               onSubmit={handleFormSubmit}
               isLoading={isSaving}
               onCancel={() => setIsEditing(false)}
            />

            {/* Media Files Section
        <MediaFilesSection reportId={reportId} role={role} /> */}
         </div>

         {/* Modals */}
         <DeleteEvidenceModal
            isOpen={showDeleteModal}
            onClose={() => setShowDeleteModal(false)}
            onConfirm={handleConfirmDelete}
            evidenceName="this field report"
         />

         <SubmitSuccessModal
            isOpen={showSuccessModal}
            onClose={() => setShowSuccessModal(false)}
            title="Field Report Saved Successfully!"
            itemName={`Case #${reportId} Field Report`}
            message="Your field report has been successfully saved."
         />
      </main>
   );
}