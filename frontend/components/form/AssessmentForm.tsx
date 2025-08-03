"use client";

import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Trash2 } from "lucide-react";
import { useParams } from "next/navigation";
import { useAppDispatch, useAppSelector } from "@/store/phase2/hooks";
import { 
  fetchAssessment, 
  saveAssessment, 
  deleteAssessment, 
  updateLocalData, 
  clearError,
  AssessmentFormData as ReduxAssessmentFormData
} from "@/store/phase2/slices/assessmentSlice";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { toast } from "sonner";

export interface AssessmentFormData {
   id: string;
   location: string;
   collector: string;
   time: string;
   overview: string;
   detailedDescription: string;
   initialCondition: string;
   preservationMeasures: string;
}

interface AssessmentFormProps {
   isEditing: boolean;
   onEdit: () => void;
   onCancel: () => void;
   className?: string;
}

export function AssessmentForm({
   isEditing,
   onEdit,
   onCancel,
   className = ""
}: AssessmentFormProps) {
   const dispatch = useAppDispatch();
   const params = useParams();
   const { data, isLoading, isSaving, error } = useAppSelector(state => state.assessment);
   
   const [formData, setFormData] = useState<AssessmentFormData>(data || {
      id: "PE 01",
      location: "A1 - Kitchen", 
      collector: "Lt. James Potter",
      time: "14:25 - 25/06/25",
      overview: "",
      detailedDescription: "",
      initialCondition: "",
      preservationMeasures: ""
   });
   const [showDeleteModal, setShowDeleteModal] = useState(false);

   // Fetch data when component mounts
   useEffect(() => {
      if (params.reportsId) {
         dispatch(fetchAssessment(params.reportsId as string));
      }
   }, [dispatch, params.reportsId]);

   // Update form data when Redux data changes
   useEffect(() => {
      if (data) {
         setFormData(data);
      }
   }, [data]);

   // Handle errors
   useEffect(() => {
      if (error) {
         toast.error("Error", {
            description: error
         });
         dispatch(clearError());
      }
   }, [error, dispatch]);

   const handleTextareaChange = (field: keyof AssessmentFormData, value: string) => {
      const updatedData = { ...formData, [field]: value };
      setFormData(updatedData);
      
      // Update Redux store for real-time sync
      dispatch(updateLocalData({ [field]: value }));
   };

   const handleSave = async () => {
      try {
         await dispatch(saveAssessment({
            reportId: params.reportsId as string,
            data: formData
         })).unwrap();
         
         toast.success("Assessment saved successfully!");
         onCancel();
      } catch (error) {
         // Error is handled by Redux and useEffect above
      }
   };

   const handleCancel = () => {
      if (data) {
         setFormData(data); // Reset to Redux data
      }
      onCancel();
   };

   const handleDeleteClick = () => {
      setShowDeleteModal(true);
   };

   const handleConfirmDelete = async () => {
      try {
         await dispatch(deleteAssessment(params.reportsId as string)).unwrap();
         toast.success("Assessment deleted successfully!");
         setShowDeleteModal(false);
      } catch (error) {
         // Error is handled by Redux and useEffect above
      }
   };

   const handleCloseModal = () => {
      setShowDeleteModal(false);
   };

   if (isLoading) {
      return (
         <div className={`space-y-6 ${className}`}>
            <div className="bg-white rounded-lg p-6 text-center">
               <p>Loading assessment...</p>
            </div>
         </div>
      );
   }

   return (
      <div className={`space-y-6 ${className}`}>
         {/* OVERVIEW SECTION */}
         <div className="bg-white rounded-lg p-6">
            <h2 className="text-xl font-bold text-gray-900 mb-4">Overview</h2>
            <div className="grid grid-cols-2 gap-4 mb-4">
               <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Assessment ID</label>
                  <div className="text-sm text-gray-900 bg-gray-50 p-2 rounded">{formData.id}</div>
               </div>
               <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Location</label>
                  <div className="text-sm text-gray-900 bg-gray-50 p-2 rounded">{formData.location}</div>
               </div>
               <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Collector</label>
                  <div className="text-sm text-gray-900 bg-gray-50 p-2 rounded">{formData.collector}</div>
               </div>
               <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Time</label>
                  <div className="text-sm text-gray-900 bg-gray-50 p-2 rounded">{formData.time}</div>
               </div>
            </div>
            <div>
               <label className="block text-sm font-medium text-gray-700 mb-2">Summary</label>
               {isEditing ? (
                  <Textarea
                     value={formData.overview}
                     onChange={(e) => handleTextareaChange('overview', e.target.value)}
                     className="w-full rounded-lg border-gray-300"
                     rows={3}
                  />
               ) : (
                  <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed">
                     {formData.overview}
                  </div>
               )}
            </div>
         </div>

         {/* DETAILED DESCRIPTION SECTION */}
         <div className="bg-white rounded-lg p-6">
            <h2 className="text-xl font-bold text-gray-900 mb-4">Detailed Description</h2>
            {isEditing ? (
               <Textarea
                  value={formData.detailedDescription}
                  onChange={(e) => handleTextareaChange('detailedDescription', e.target.value)}
                  className="w-full rounded-lg border-gray-300"
                  rows={8}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-4 rounded-lg leading-relaxed">
                  {formData.detailedDescription}
               </div>
            )}
         </div>

         {/* INITIAL CONDITION SECTION */}
         <div className="bg-white rounded-lg p-6">
            <h2 className="text-xl font-bold text-gray-900 mb-4">Initial Condition</h2>
            {isEditing ? (
               <Textarea
                  value={formData.initialCondition}
                  onChange={(e) => handleTextareaChange('initialCondition', e.target.value)}
                  className="w-full rounded-lg border-gray-300"
                  rows={6}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-4 rounded-lg leading-relaxed">
                  {formData.initialCondition}
               </div>
            )}
         </div>

         {/* INITIAL PRESERVATION MEASURES SECTION */}
         <div className="bg-white rounded-lg p-6">
            <h2 className="text-xl font-bold text-gray-900 mb-4">Initial Preservation Measures</h2>
            {isEditing ? (
               <Textarea
                  value={formData.preservationMeasures}
                  onChange={(e) => handleTextareaChange('preservationMeasures', e.target.value)}
                  className="w-full rounded-lg border-gray-300"
                  rows={6}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-4 rounded-lg leading-relaxed">
                  {formData.preservationMeasures}
               </div>
            )}
         </div>

         {/* ACTION BUTTONS */}
         <div className="flex justify-end items-center bg-white p-4 rounded-lg">
            <div className="flex gap-4">
               {isEditing ? (
                  <>
                     <Button variant="outline" onClick={handleCancel} className="rounded-full" disabled={isSaving}>
                        Cancel
                     </Button>
                     <Button onClick={handleSave} className="rounded-full" disabled={isSaving}>
                        {isSaving ? "Saving..." : "Save Changes"}
                     </Button>
                  </>
               ) : (
                  <Button onClick={onEdit} className="rounded-full">
                     Edit Assessment
                  </Button>
               )}

               <Button
                  variant="destructive"
                  onClick={handleDeleteClick}
                  className="flex items-center gap-2 rounded-full"
                  disabled={isSaving}
               >
                  <Trash2 className="w-4 h-4" />
                  {isSaving ? "Deleting..." : "Delete"}
               </Button>
            </div>
         </div>

         {/* DELETE CONFIRMATION MODAL */}
         <DeleteEvidenceModal
            isOpen={showDeleteModal}
            onClose={handleCloseModal}
            onConfirm={handleConfirmDelete}
            evidenceName={`Assessment ${formData.id} - ${formData.location}`}
         />
      </div>
   );
}
