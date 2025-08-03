"use client";

import { useState, useEffect } from "react";
import { useParams } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Trash2 } from "lucide-react";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { FileUploadWithPreview, UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";
import { useAppDispatch, useAppSelector } from "@/store/phase2/hooks";
import { fetchScenePreservation, saveScenePreservation, deleteScenePreservation, updateLocalData, clearError } from "@/store/phase2/slices/scenePreservationSlice";
import { toast } from "@/hooks/use-toast";

export interface ScenePreservationFormData {
   responsibleUnit: string;
   arrivalTime: string;
   startTime: string;
   endTime: string;
   startIsAM: boolean;
   endIsAM: boolean;
   description: string;
   areaCovered: string;
   notes: string;
}

interface ScenePreservationFormProps {
   isEditing: boolean;
   onEdit: () => void;
   onCancel: () => void;
   className?: string;
}

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

export function ScenePreservationForm({
   isEditing,
   onEdit,
   onCancel,
   className = ""
}: ScenePreservationFormProps) {
   const dispatch = useAppDispatch();
   const params = useParams();
   const { data, isLoading, isSaving, error } = useAppSelector(state => state.scenePreservation);
   
   const [formData, setFormData] = useState<ScenePreservationFormData>(data || {
      responsibleUnit: "",
      arrivalTime: "",
      startTime: "",
      endTime: "",
      startIsAM: true,
      endIsAM: true,
      description: "",
      areaCovered: "",
      notes: ""
   });
   const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>([]);
   const [showDeleteModal, setShowDeleteModal] = useState(false);

   // Fetch data when component mounts
   useEffect(() => {
      if (params.reportsId) {
         dispatch(fetchScenePreservation(params.reportsId as string));
      }
   }, [dispatch, params.reportsId]);

   // Update form data when Redux data changes
   useEffect(() => {
      if (data) {
         setFormData({
            responsibleUnit: data.responsibleUnit,
            arrivalTime: data.arrivalTime,
            startTime: data.startTime,
            endTime: data.endTime,
            startIsAM: data.startIsAM,
            endIsAM: data.endIsAM,
            description: data.description,
            areaCovered: data.areaCovered,
            notes: data.notes
         });
         setUploadedFiles(data.files || []);
      }
   }, [data]);

   // Handle errors
   useEffect(() => {
      if (error) {
         toast({
            variant: "destructive",
            title: "Error",
            description: error
         });
         dispatch(clearError());
      }
   }, [error, dispatch]);

   // Check if data has content (not all blank)
   const hasContent = () => {
      return formData.responsibleUnit.trim() !== "" ||
             formData.arrivalTime.trim() !== "" ||
             formData.startTime.trim() !== "" ||
             formData.endTime.trim() !== "" ||
             formData.description.trim() !== "" ||
             formData.areaCovered.trim() !== "" ||
             formData.notes.trim() !== "" ||
             uploadedFiles.length > 0;
   };

   const handleInputChange = (field: keyof ScenePreservationFormData, value: string | boolean) => {
      const updatedData = { ...formData, [field]: value };
      setFormData(updatedData);
      
      // Update Redux store for real-time sync
      dispatch(updateLocalData({ [field]: value }));
   };

   const handleSave = async () => {
      try {
         const dataToSave = { ...formData, files: uploadedFiles };
         await dispatch(saveScenePreservation({
            reportId: params.reportsId as string,
            data: dataToSave
         })).unwrap();
         
         toast({
            title: "Success",
            description: "Scene preservation saved successfully!"
         });
         onCancel();
      } catch (error) {
         // Error is handled by Redux and useEffect above
      }
   };

   const handleCancel = () => {
      if (data) {
         setFormData({
            responsibleUnit: data.responsibleUnit,
            arrivalTime: data.arrivalTime,
            startTime: data.startTime,
            endTime: data.endTime,
            startIsAM: data.startIsAM,
            endIsAM: data.endIsAM,
            description: data.description,
            areaCovered: data.areaCovered,
            notes: data.notes
         });
         setUploadedFiles(data.files || []);
      }
      onCancel();
   };

   const handleClearAll = () => {
      // Clean up preview URLs
      uploadedFiles.forEach(uploadedFile => {
         if (uploadedFile.preview) {
            URL.revokeObjectURL(uploadedFile.preview);
         }
      });
      setFormData({
         responsibleUnit: "",
         arrivalTime: "",
         startTime: "",
         endTime: "",
         startIsAM: true,
         endIsAM: true,
         description: "",
         areaCovered: "",
         notes: ""
      });
      setUploadedFiles([]);
   };

   const handleDeleteClick = () => {
      setShowDeleteModal(true);
   };

   const handleConfirmDelete = async () => {
      try {
         // Clean up preview URLs
         uploadedFiles.forEach(uploadedFile => {
            if (uploadedFile.preview) {
               URL.revokeObjectURL(uploadedFile.preview);
            }
         });
         
         await dispatch(deleteScenePreservation(params.reportsId as string)).unwrap();
         toast({
            title: "Success",
            description: "Scene preservation deleted successfully!"
         });
         setShowDeleteModal(false);
      } catch (error) {
         // Error is handled by Redux and useEffect above
      }
   };

   const handleCloseModal = () => {
      setShowDeleteModal(false);
   };

   const handleFilesChange = (files: UploadedFile[]) => {
      setUploadedFiles(files);
   };

   if (isLoading) {
      return (
         <div className={`space-y-6 ${className}`}>
            <div className="bg-white rounded-lg p-6 text-center">
               <p>Loading scene preservation...</p>
            </div>
         </div>
      );
   }

   return (
      <div className={`space-y-6 ${className}`}>
         {/* RESPONSIBLE UNIT/OFFICER SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               RESPONSIBLE UNIT/OFFICER
            </label>
            {isEditing ? (
               <Input
                  value={formData.responsibleUnit}
                  onChange={(e) => handleInputChange('responsibleUnit', e.target.value)}
                  placeholder="Enter responsible unit/officer..."
                  className="w-full rounded-lg"
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                  {formData.responsibleUnit || <span className="text-gray-500 italic">No responsible unit/officer specified</span>}
               </div>
            )}
         </div>

         {/* TIME OF ARRIVAL SECTION */}
         <div className="bg-white rounded-lg p-6">
            <div className="flex justify-between items-center">
               <label className="font-semibold text-lg">
                  TIME OF ARRIVAL AT THE SCENE
               </label>
               <div className="flex items-center gap-2">
                  {isEditing ? (
                     <Input 
                        className="w-20 rounded-full" 
                        value={formData.arrivalTime}
                        onChange={(e) => handleInputChange('arrivalTime', e.target.value)}
                        placeholder="HH:MM"
                        disabled={isSaving}
                     />
                  ) : (
                     <div className="text-sm text-gray-900 bg-gray-50 px-3 py-1 rounded-full min-w-[80px] text-center">
                        {formData.arrivalTime || <span className="text-gray-500 italic">--:--</span>}
                     </div>
                  )}
               </div>
            </div>
         </div>

         {/* START TIME SECTION */}
         <div className="bg-white rounded-lg p-6">
            <div className="flex justify-between items-center">
               <label className="font-semibold text-lg">
                  START TIME
               </label>
               <div className="flex items-center gap-2">
                  {isEditing ? (
                     <>
                        <Input 
                           className="w-20 rounded-full" 
                           value={formData.startTime}
                           onChange={(e) => handleInputChange('startTime', e.target.value)}
                           placeholder="HH:MM"
                           disabled={isSaving}
                        />
                        <TimeToggle 
                           isAM={formData.startIsAM} 
                           setIsAM={(value) => handleInputChange('startIsAM', value)} 
                           disabled={isSaving}
                        />
                     </>
                  ) : (
                     <div className="flex items-center gap-2">
                        <div className="text-sm text-gray-900 bg-gray-50 px-3 py-1 rounded-full min-w-[80px] text-center">
                           {formData.startTime || <span className="text-gray-500 italic">--:--</span>}
                        </div>
                        <div className="text-sm text-gray-900 bg-gray-50 px-3 py-1 rounded-full min-w-[60px] text-center">
                           {formData.startTime ? (formData.startIsAM ? 'AM' : 'PM') : <span className="text-gray-500 italic">--</span>}
                        </div>
                     </div>
                  )}
               </div>
            </div>
         </div>

         {/* END TIME SECTION */}
         <div className="bg-white rounded-lg p-6">
            <div className="flex justify-between items-center">
               <label className="font-semibold text-lg">
                  END TIME
               </label>
               <div className="flex items-center gap-2">
                  {isEditing ? (
                     <>
                        <Input 
                           className="w-20 rounded-full" 
                           value={formData.endTime}
                           onChange={(e) => handleInputChange('endTime', e.target.value)}
                           placeholder="HH:MM"
                           disabled={isSaving}
                        />
                        <TimeToggle 
                           isAM={formData.endIsAM} 
                           setIsAM={(value) => handleInputChange('endIsAM', value)} 
                           disabled={isSaving}
                        />
                     </>
                  ) : (
                     <div className="flex items-center gap-2">
                        <div className="text-sm text-gray-900 bg-gray-50 px-3 py-1 rounded-full min-w-[80px] text-center">
                           {formData.endTime || <span className="text-gray-500 italic">--:--</span>}
                        </div>
                        <div className="text-sm text-gray-900 bg-gray-50 px-3 py-1 rounded-full min-w-[60px] text-center">
                           {formData.endTime ? (formData.endIsAM ? 'AM' : 'PM') : <span className="text-gray-500 italic">--</span>}
                        </div>
                     </div>
                  )}
               </div>
            </div>
         </div>

         {/* DESCRIPTION SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               DESCRIPTION OF THE SCENE PRESERVATION METHODS
            </label>
            {isEditing ? (
               <Textarea
                  value={formData.description}
                  onChange={(e) => handleInputChange('description', e.target.value)}
                  placeholder="Enter description of preservation methods..."
                  className="w-full rounded-lg border-gray-300"
                  rows={6}
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[8rem] whitespace-pre-line">
                  {formData.description || <span className="text-gray-500 italic">No preservation methods described</span>}
               </div>
            )}
         </div>

         {/* AREA COVERED SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               AREA COVERED / PERIMETER
            </label>
            {isEditing ? (
               <Textarea
                  value={formData.areaCovered}
                  onChange={(e) => handleInputChange('areaCovered', e.target.value)}
                  placeholder="Enter area covered and perimeter details..."
                  className="w-full rounded-lg border-gray-300"
                  rows={4}
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[6rem] whitespace-pre-line">
                  {formData.areaCovered || <span className="text-gray-500 italic">No area coverage details</span>}
               </div>
            )}
         </div>

         {/* NOTES SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               NOTES / SPECIAL INSTRUCTIONS
            </label>
            {isEditing ? (
               <Textarea
                  value={formData.notes}
                  onChange={(e) => handleInputChange('notes', e.target.value)}
                  placeholder="Enter notes or special instructions..."
                  className="w-full rounded-lg border-gray-300"
                  rows={5}
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[7rem] whitespace-pre-line">
                  {formData.notes || <span className="text-gray-500 italic">No notes or special instructions</span>}
               </div>
            )}
         </div>

         {/* ATTACHMENTS SECTION - Using the reusable component */}
         <FileUploadWithPreview
            uploadedFiles={uploadedFiles}
            onFilesChange={handleFilesChange}
            isEditing={isEditing}
            title="ATTACHMENTS"
            description="Supports: JPG, PNG, PDF, DOC, MP4, MOV files"
            accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.mp4,.mov,.avi"
            maxFiles={20}
         />

         {/* ACTION BUTTONS */}
         <div className="flex justify-end items-center bg-white p-4 rounded-lg">
            <div className="flex gap-4">
               {isEditing ? (
                  <>
                     <Button variant="outline" onClick={handleCancel} className="rounded-full" disabled={isSaving}>
                        Cancel
                     </Button>
                     <Button variant="outline" onClick={handleClearAll} className="rounded-full text-red-600 hover:text-red-700" disabled={isSaving}>
                        Clear All
                     </Button>
                     <Button onClick={handleSave} className="rounded-full" disabled={isSaving}>
                        {isSaving ? 'Saving...' : 'Save Changes'}
                     </Button>
                  </>
               ) : (
                  <Button onClick={onEdit} className="rounded-full">
                     Edit Preservation Measures
                  </Button>
               )}

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

         {/* DELETE CONFIRMATION MODAL */}
         <DeleteEvidenceModal
            isOpen={showDeleteModal}
            onClose={handleCloseModal}
            onConfirm={handleConfirmDelete}
            evidenceName="these scene preservation measures"
         />
      </div>
   );
}
