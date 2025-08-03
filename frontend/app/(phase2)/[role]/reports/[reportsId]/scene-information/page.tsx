"use client";

import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Calendar, MonitorUp } from "lucide-react";
import { SectionContainer } from "@/components/features/phase2/SectionContainer";
import { DataTable } from "@/components/features/phase2/DataTable";
import { ActionButtons } from "@/components/features/phase2/ActionButtons";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { useRouter, useParams } from "next/navigation";

/**
 * SCENE INFORMATION PAGE
 *
 * This is the main page component for the Scene Information section of the police report system.
 * It displays various forms and tables for collecting scene information data including:
 * - Initial Statements
 * - Scene Description
 * - Images and Videos
 * - Preliminary Physical Evidence Information
 *
 * The page uses reusable components (SectionContainer, DataTable, ActionButtons)
 * to maintain consistency and reduce code duplication.
 */

// Initial mock data for different sections
// These would typically come from a database or API
const initialStatementInit = [
   {
      title: "Witness",
      provider: "Ms. A",
      date: "15/06/2025"
   },
   {
      title: "Witness",
      provider: "Ms. B",
      date: "11/04/2025"
   },
   {
      title: "Victim",
      provider: "Ms. C",
      date: "17/05/2025"
   }
];

const sceneDescriptionInit = [
   {
      title: "Scene ________",
      provider: "Ms. A",
      date: "15/06/2025"
   },
   {
      title: "Scene ________",
      provider: "Ms. B",
      date: "11/04/2025"
   },
   {
      title: "Scene ________",
      provider: "Ms. C",
      date: "17/05/2025"
   }
];

const imagesAndVideosInit = [
   {
      name: "________",
      description: "________",
      date: "15/06/2025"
   },
   {
      name: "________",
      description: "________",
      date: "11/04/2025"
   },
   {
      name: "________",
      description: "________",
      date: "17/05/2025"
   }
];

const preliminaryInvestigationInit = [
   {
      name: "Scene ________",
      description: "________",
      date: "15/06/2025"
   },
   {
      name: "Scene ________",
      description: "________",
      date: "11/04/2025"
   },
   {
      name: "Scene ________",
      description: "________",
      date: "17/05/2025"
   }
];

export default function Page() {
   // State management for all data sections
   // These states hold the current data for each table/section
   const [statements, setStatements] = useState(initialStatementInit);
   const [sceneDescriptions, setSceneDescriptions] = useState(sceneDescriptionInit);
   const [imagesAndVideos, setImagesAndVideos] = useState(imagesAndVideosInit);
   const [preliminaryInvestigation, setPreliminaryInvestigation] = useState(preliminaryInvestigationInit);

   // State for controlling the delete modal
   const [showDeleteModal, setShowDeleteModal] = useState(false);
   const [evidenceToDelete, setEvidenceToDelete] = useState<any>(null);

   // Router and params for navigation
   const router = useRouter();
   const params = useParams();

   /**
    * COLUMN CONFIGURATIONS
    *
    * These objects define the structure of each table:
    * - key: the property name in the data object
    * - label: the display name for the column header
    * - render: optional custom render function for special formatting
    */

   // For Initial Statements table
   const statementColumns = [
      { key: "index", label: "#", render: (_: any, __: any, index: number) => index + 1 },
      { key: "title", label: "Statement Type" },
      { key: "provider", label: "Provider" },
      { key: "date", label: "Date" },
   ];

   // For Scene Description table
   const sceneDescriptionColumns = [
      { key: "index", label: "#", render: (_: any, __: any, index: number) => index + 1 },
      { key: "title", label: "Description Title" },
      { key: "provider", label: "Provider" },
      { key: "date", label: "Date" },
   ];

   // For Images and Videos table
   const imagesAndVideosColumns = [
      { key: "index", label: "#", render: (_: any, __: any, index: number) => index + 1 },
      { key: "name", label: "File Name" },
      { key: "description", label: "Description" },
      { key: "date", label: "Date" },
   ];

   // For Preliminary Physical Evidence table
   const preliminaryInvestigationColumns = [
      { key: "index", label: "#", render: (_: any, __: any, index: number) => index + 1 },
      { key: "name", label: "Evidence Name" },
      { key: "description", label: "Description" },
      { key: "date", label: "Date" },
   ];

   /**
    * EVENT HANDLERS
    *
    * These functions handle user interactions like adding, editing, and deleting items.
    * Currently they just log to console, but in a real application they would:
    * - Open modals/forms for editing
    * - Make API calls to save/update/delete data
    * - Update the local state
    * - Show success/error messages
    */

   // Add handlers - would typically open a form modal
   const handleAddStatement = () => {
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information/statements/details`);
   };

   const handleAddSceneDescription = () => {
      console.log("Add scene description");
      // TODO: Open add scene description modal/form
   };

   const handleAddImagesVideos = () => {
      console.log("Add images/videos");
      // TODO: Open add images/videos modal/form
   };

   const handleAddPreliminaryEvidence = () => {
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information/evidence`);
   };

   // Edit handlers - would typically open a pre-filled form modal
   const handleEditStatement = (statement: any, index: number) => {
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information/statements/details`);
   };

   const handleEditSceneDescription = (description: any, index: number) => {
      console.log("Edit scene description:", description, index);
      // TODO: Open edit scene description modal with pre-filled data
   };

   const handleEditImagesVideos = (media: any, index: number) => {
      console.log("Edit images/videos:", media, index);
      // TODO: Open edit images/videos modal with pre-filled data
   };

   const handleEditPreliminaryEvidence = (evidence: any, index: number) => {
      console.log("Edit preliminary evidence:", evidence, index);
      // TODO: Open edit preliminary evidence modal with pre-filled data
   };

   // Delete handlers - would typically show confirmation dialog then delete
   const handleDeleteStatement = (statement: any, index: number) => {
      setEvidenceToDelete({ item: statement, index, type: 'statement' });
      setShowDeleteModal(true);
   };

   const handleDeleteSceneDescription = (description: any, index: number) => {
      setEvidenceToDelete({ item: description, index, type: 'description' });
      setShowDeleteModal(true);
   };

   const handleDeleteImagesVideos = (media: any, index: number) => {
      setEvidenceToDelete({ item: media, index, type: 'media' });
      setShowDeleteModal(true);
   };

   const handleDeletePreliminaryEvidence = (evidence: any, index: number) => {
      setEvidenceToDelete({ item: evidence, index, type: 'evidence' });
      setShowDeleteModal(true);
   };
      // View handlers - would typically open a detail view or navigate to detail page
   const handleViewStatement = (statement: any, index: number) => {
      router.push(`/${params.role}/reports/${params.reportsId}/scene-information/statements/details`);
   };

   const handleViewSceneDescription = (description: any, index: number) => {
      console.log("View scene description details:", description, index);
      // TODO: Open scene description detail modal or navigate to detail page
   };

   const handleViewImagesVideos = (media: any, index: number) => {
      console.log("View images/videos details:", media, index);
      // TODO: Open images/videos detail modal or navigate to detail page
   };

   const handleViewPreliminaryEvidence = (evidence: any, index: number) => {
      console.log("View preliminary evidence details:", evidence, index);
      // TODO: Open preliminary evidence detail modal or navigate to detail page
   };

   // Confirm deletion handler
   const handleConfirmDelete = () => {
      if (!evidenceToDelete) return;

      const { item, index, type } = evidenceToDelete;
      
      // Perform actual deletion based on type
      switch (type) {
         case 'statement':
            setStatements(prev => prev.filter((_, i) => i !== index));
            console.log("Statement deleted:", item, index);
            break;
         case 'description':
            setSceneDescriptions(prev => prev.filter((_, i) => i !== index));
            console.log("Scene description deleted:", item, index);
            break;
         case 'media':
            setImagesAndVideos(prev => prev.filter((_, i) => i !== index));
            console.log("Images/videos deleted:", item, index);
            break;
         case 'evidence':
            setPreliminaryInvestigation(prev => prev.filter((_, i) => i !== index));
            console.log("Preliminary evidence deleted:", item, index);
            break;
      }

      // Reset modal state
      setEvidenceToDelete(null);
      setShowDeleteModal(false);
   };

   // Close modal handler
   const handleCloseModal = () => {
      setShowDeleteModal(false);
      setEvidenceToDelete(null);
   };

   return (
      <main className="flex-1 p-6">
         {/* Page Header */}
         <h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
            SCENE INFORMATION
         </h1>

         {/* Main Content Container with gray background */}
         <div className="bg-gray-300 rounded-b-lg shadow p-6 pt-10">

            {/* INITIAL STATEMENTS SECTION */}
            <SectionContainer
               label="INITIAL STATEMENTS"
               onAdd={handleAddStatement}
               addButtonText="View Details"
               icon={false} // No icon for this button
            >
               <DataTable
                  columns={statementColumns}
                  data={statements}
                  actions={(row, index) => (
                     <ActionButtons
                        row={row}
                        index={index}
                        onEdit={handleEditStatement}
                        onDelete={handleDeleteStatement}
                        onView={handleViewStatement}
                     />
                  )}
               />
            </SectionContainer>

            {/* SCENE DESCRIPTION SECTION */}
            <SectionContainer
               label="SCENE DESCRIPTION"
               onAdd={handleAddSceneDescription}
            >
               <DataTable
                  columns={sceneDescriptionColumns}
                  data={sceneDescriptions}
                  actions={(row, index) => (
                     <ActionButtons
                        row={row}
                        index={index}
                        onEdit={handleEditSceneDescription}
                        onDelete={handleDeleteSceneDescription}
                        onView={handleViewSceneDescription}
                     />
                  )}
               />
            </SectionContainer>

            {/* IMAGES AND VIDEOS SECTION */}
            <SectionContainer
               label="IMAGES AND VIDEOS"
               onAdd={handleAddImagesVideos}
            >
               <DataTable
                  columns={imagesAndVideosColumns}
                  data={imagesAndVideos}
                  actions={(row, index) => (
                     <ActionButtons
                        row={row}
                        index={index}
                        onEdit={handleEditImagesVideos}
                        onDelete={handleDeleteImagesVideos}
                        onView={handleViewImagesVideos}
                     />
                  )}
               />
            </SectionContainer>

            {/* PRELIMINARY PHYSICAL EVIDENCE INFORMATION SECTION */}
            <SectionContainer
               label="PRELIMINARY PHYSICAL EVIDENCE INFORMATION"
               onAdd={handleAddPreliminaryEvidence}
               className="mb-8"
            >
               <DataTable
                  columns={preliminaryInvestigationColumns}
                  data={preliminaryInvestigation}
                  actions={(row, index) => (
                     <ActionButtons
                        row={row}
                        index={index}
                        onEdit={handleEditPreliminaryEvidence}
                        onDelete={handleDeletePreliminaryEvidence}
                        onView={handleViewPreliminaryEvidence}
                     />
                  )}
               />
            </SectionContainer>

            {/* PAGE ACTION BUTTONS */}
            {/* Cancel and Save buttons for the entire form */}
            <div className="flex justify-end gap-4 bg-white p-4 rounded-lg">
               <Button variant="outline" className="rounded-full">
                  Cancel
               </Button>
               <Button className="rounded-full">Save</Button>
            </div>
         </div>

         {/* DELETE CONFIRMATION MODAL */}
         <DeleteEvidenceModal
            isOpen={showDeleteModal}
            onClose={handleCloseModal}
            onConfirm={handleConfirmDelete}
            evidenceName={evidenceToDelete?.item?.name || evidenceToDelete?.item?.title || "this evidence"}
         />
      </main>
   );
}