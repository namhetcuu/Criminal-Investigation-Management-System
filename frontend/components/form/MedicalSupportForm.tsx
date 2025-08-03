"use client";

import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Trash2 } from "lucide-react";
import { useParams } from "next/navigation";
import { useAppDispatch, useAppSelector } from "@/store/phase2/hooks";
import { 
  fetchMedicalSupport, 
  saveMedicalSupport, 
  deleteMedicalSupport, 
  updateLocalData, 
  clearError,
  MedicalSupportFormData as ReduxMedicalSupportFormData
} from "@/store/phase2/slices/medicalSupportSlice";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { FileUploadWithPreview, UploadedFile } from "@/components/features/phase2/FileUploadWithPreview";
import { toast } from "sonner";

export interface MedicalSupportFormData {
   unitId: string;
   supportType: string;
   personnelAssigned: string;
   locationAssigned: string;
   remarks: string;
}

interface MedicalSupportFormProps {
   isEditing: boolean;
   onEdit: () => void;
   onCancel: () => void;
   className?: string;
}

export function MedicalSupportForm({
   isEditing,
   onEdit,
   onCancel,
   className = ""
}: MedicalSupportFormProps) {
   const dispatch = useAppDispatch();
   const params = useParams();
   const { data, isLoading, isSaving, error } = useAppSelector(state => state.medicalSupport);
   
   const [formData, setFormData] = useState<MedicalSupportFormData>(data || {
      unitId: "",
      supportType: "",
      personnelAssigned: "",
      locationAssigned: "",
      remarks: ""
   });
   const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>([]);
   const [showDeleteModal, setShowDeleteModal] = useState(false);

   // Fetch data when component mounts
   useEffect(() => {
      if (params.reportsId) {
         dispatch(fetchMedicalSupport(params.reportsId as string));
      }
   }, [dispatch, params.reportsId]);

   // Update form data when Redux data changes
   useEffect(() => {
      if (data) {
         setFormData({
            unitId: data.unitId,
            supportType: data.supportType,
            personnelAssigned: data.personnelAssigned,
            locationAssigned: data.locationAssigned,
            remarks: data.remarks
         });
         setUploadedFiles(data.files || []);
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

   // Check if data has content (not all blank)
   const hasContent = () => {
      return formData.unitId.trim() !== "" ||
             formData.supportType.trim() !== "" ||
             formData.personnelAssigned.trim() !== "" ||
             formData.locationAssigned.trim() !== "" ||
             formData.remarks.trim() !== "" ||
             uploadedFiles.length > 0;
   };

   const handleInputChange = (field: keyof MedicalSupportFormData, value: string) => {
      const updatedData = { ...formData, [field]: value };
      setFormData(updatedData);
      
      // Update Redux store for real-time sync
      dispatch(updateLocalData({ [field]: value }));
   };

   const handleSave = async () => {
      try {
         const dataToSave = { ...formData, files: uploadedFiles };
         await dispatch(saveMedicalSupport({
            reportId: params.reportsId as string,
            data: dataToSave
         })).unwrap();
         
         toast.success("Medical support saved successfully!");
         onCancel();
      } catch (error) {
         // Error is handled by Redux and useEffect above
      }
   };

   const handleCancel = () => {
      if (data) {
         setFormData({
            unitId: data.unitId,
            supportType: data.supportType,
            personnelAssigned: data.personnelAssigned,
            locationAssigned: data.locationAssigned,
            remarks: data.remarks
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
         unitId: "",
         supportType: "",
         personnelAssigned: "",
         locationAssigned: "",
         remarks: ""
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
         
         await dispatch(deleteMedicalSupport(params.reportsId as string)).unwrap();
         toast.success("Medical support deleted successfully!");
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
               <p>Loading medical support...</p>
            </div>
         </div>
      );
   }

   return (
      <div className={`space-y-6 ${className}`}>
         {/* MEDICAL/RESCUE UNIT ID SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               MEDICAL/RESCUE UNIT ID
            </label>
            {isEditing ? (
               <Input
                  value={formData.unitId}
                  onChange={(e) => handleInputChange('unitId', e.target.value)}
                  placeholder="Enter medical/rescue unit ID..."
                  className="w-full rounded-lg"
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                  {formData.unitId || <span className="text-gray-500 italic">No unit ID specified</span>}
               </div>
            )}
         </div>

         {/* TYPE OF SUPPORT PROVIDED SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               TYPE OF SUPPORT PROVIDED
            </label>
            {isEditing ? (
               <Input
                  value={formData.supportType}
                  onChange={(e) => handleInputChange('supportType', e.target.value)}
                  placeholder="Enter type of support provided..."
                  className="w-full rounded-lg"
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                  {formData.supportType || <span className="text-gray-500 italic">No support type specified</span>}
               </div>
            )}
         </div>

         {/* PERSONNEL ASSIGNED SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               PERSONNEL ASSIGNED
            </label>
            {isEditing ? (
               <Textarea
                  value={formData.personnelAssigned}
                  onChange={(e) => handleInputChange('personnelAssigned', e.target.value)}
                  placeholder="Enter personnel assigned details..."
                  className="w-full rounded-lg border-gray-300"
                  rows={4}
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[6rem] whitespace-pre-line">
                  {formData.personnelAssigned || <span className="text-gray-500 italic">No personnel information specified</span>}
               </div>
            )}
         </div>

         {/* LOCATION ASSIGNED SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               LOCATION ASSIGNED
            </label>
            {isEditing ? (
               <Input
                  value={formData.locationAssigned}
                  onChange={(e) => handleInputChange('locationAssigned', e.target.value)}
                  placeholder="Enter location assigned..."
                  className="w-full rounded-lg"
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[2.5rem] flex items-center">
                  {formData.locationAssigned || <span className="text-gray-500 italic">No location specified</span>}
               </div>
            )}
         </div>

         {/* REMARKS/NOTES SECTION */}
         <div className="bg-white rounded-lg p-6">
            <label className="block font-semibold text-lg mb-4">
               REMARKS/NOTES
            </label>
            {isEditing ? (
               <Textarea
                  value={formData.remarks}
                  onChange={(e) => handleInputChange('remarks', e.target.value)}
                  placeholder="Enter remarks or additional notes..."
                  className="w-full rounded-lg border-gray-300"
                  rows={6}
                  disabled={isSaving}
               />
            ) : (
               <div className="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg leading-relaxed min-h-[8rem] whitespace-pre-line">
                  {formData.remarks || <span className="text-gray-500 italic">No remarks or notes</span>}
               </div>
            )}
         </div>

         {/* SCENE SKETCH SECTION - Using the reusable component */}
         <FileUploadWithPreview
            uploadedFiles={uploadedFiles}
            onFilesChange={handleFilesChange}
            isEditing={isEditing}
            title="SCENE SKETCH"
            description="Supports: JPG, PNG, PDF, DOC files"
            accept=".jpg,.jpeg,.png,.pdf,.doc,.docx"
            maxFiles={10}
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
                     Edit Medical Support
                  </Button>
               )}

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
            evidenceName={formData.unitId ? `Medical Support ${formData.unitId}` : "this medical support record"}
         />
      </div>
   );
}
