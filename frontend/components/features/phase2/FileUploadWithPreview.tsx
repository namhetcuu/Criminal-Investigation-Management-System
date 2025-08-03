"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Upload, Cloud, Trash2, Eye, X, Download } from "lucide-react";

// File interface for better type safety
export interface UploadedFile {
   file: File;
   preview?: string;
}

interface FileUploadWithPreviewProps {
   uploadedFiles: UploadedFile[];
   onFilesChange: (files: UploadedFile[]) => void;
   isEditing: boolean;
   accept?: string;
   maxFiles?: number;
   title?: string;
   description?: string;
}

export function FileUploadWithPreview({
   uploadedFiles,
   onFilesChange,
   isEditing,
   accept = ".jpg,.jpeg,.png,.pdf,.doc,.docx",
   maxFiles = 10,
   title = "Files",
   description = "Supports: JPG, PNG, PDF, DOC files"
}: FileUploadWithPreviewProps) {
   // State for image preview modal
   const [previewImage, setPreviewImage] = useState<string | null>(null);

   // Check if file is an image
   const isImageFile = (file: File) => {
      return file.type.startsWith('image/');
   };

   // Get file type icon color
   const getFileTypeColor = (file: File) => {
      if (file.type.startsWith('image/')) return 'text-green-600';
      if (file.type.includes('pdf')) return 'text-red-600';
      if (file.type.includes('document') || file.type.includes('word')) return 'text-blue-600';
      return 'text-gray-600';
   };

   const getImagePreviewUrl = (uploadedFile: UploadedFile) => {
      // If preview URL exists and is valid, use it
      if (uploadedFile.preview) {
         return uploadedFile.preview;
      }
      
      // If no preview URL but it's an image file, create one
      if (isImageFile(uploadedFile.file)) {
         try {
            const newPreview = URL.createObjectURL(uploadedFile.file);
            // Update the uploadedFile with the new preview URL
            uploadedFile.preview = newPreview;
            return newPreview;
         } catch (error) {
            console.error('Error creating object URL:', error);
            return null;
         }
      }
      
      return null;
   };

   // File upload handlers
   const handleFileUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
      const files = event.target.files;
      if (files) {
         const newFiles = Array.from(files).slice(0, maxFiles - uploadedFiles.length).map(file => {
            const uploadedFile: UploadedFile = { file };
            
            // Create preview URL for images
            if (isImageFile(file)) {
               uploadedFile.preview = URL.createObjectURL(file);
            }
            
            return uploadedFile;
         });
         
         onFilesChange([...uploadedFiles, ...newFiles]);
      }
   };

   const handleDragOver = (e: React.DragEvent) => {
      e.preventDefault();
   };

   const handleDrop = (e: React.DragEvent) => {
      e.preventDefault();
      const files = e.dataTransfer.files;
      if (files) {
         const newFiles = Array.from(files).slice(0, maxFiles - uploadedFiles.length).map(file => {
            const uploadedFile: UploadedFile = { file };
            
            // Create preview URL for images
            if (isImageFile(file)) {
               uploadedFile.preview = URL.createObjectURL(file);
            }
            
            return uploadedFile;
         });
         
         onFilesChange([...uploadedFiles, ...newFiles]);
      }
   };

   const handleBrowseClick = () => {
      document.getElementById('fileInput')?.click();
   };

   const removeFile = (index: number) => {
      const fileToRemove = uploadedFiles[index];
      
      // Clean up preview URL
      if (fileToRemove.preview) {
         URL.revokeObjectURL(fileToRemove.preview);
      }
      
      const newFiles = uploadedFiles.filter((_, i) => i !== index);
      onFilesChange(newFiles);
   };

   const handlePreviewImage = (preview: string) => {
      setPreviewImage(preview);
   };

   const closePreview = () => {
      setPreviewImage(null);
   };

   return (
      <>
         <div className="bg-white rounded-lg p-6">
         <div className="flex justify-between items-center mb-4">
            <label className="font-semibold text-lg">
            {title}
            </label>
            {isEditing && (
            <Button
               onClick={handleBrowseClick}
               className="flex items-center gap-2 rounded-lg"
               disabled={uploadedFiles.length >= maxFiles}
            >
               <Upload className="w-4 h-4" />
               Upload Files
            </Button>
            )}
         </div>

         {isEditing ? (
            <>
            {/* File Upload Area */}
            <div
               className="border-2 border-dashed border-gray-300 rounded-lg p-8 bg-gray-50 text-center transition-colors hover:border-gray-400 hover:bg-gray-100 cursor-pointer"
               onDragOver={handleDragOver}
               onDrop={handleDrop}
               onClick={handleBrowseClick}
            >
               <Cloud className="w-16 h-16 text-gray-400 mx-auto mb-4" />
               <p className="text-lg text-gray-600 mb-2">
               Drag & drop files or  
               <span className="underline text-purple-600 cursor-pointer ms-1">Browse</span>
               </p>
               <p className="text-sm text-gray-500">
               {description}
               </p>
            </div>

            {/* Hidden File Input */}
            <input
               id="fileInput"
               type="file"
               multiple
               accept={accept}
               onChange={handleFileUpload}
               className="hidden"
            />
            </>
         ) : (
            /* VIEW MODE - File Gallery */
            <div className="text-sm text-gray-900 bg-gray-50 p-4 rounded-lg leading-relaxed min-h-[6rem]">
            {uploadedFiles.length > 0 ? (
               <div className="space-y-4">
               {/* Image Preview Grid */}
               {uploadedFiles.filter(uploadedFile => isImageFile(uploadedFile.file)).length > 0 && (
                  <div>
                  <h4 className="font-medium text-gray-900 mb-3">Images:</h4>
                  <div className="grid grid-cols-3 gap-3">
                     {uploadedFiles
                     .filter(uploadedFile => isImageFile(uploadedFile.file))
                     .map((uploadedFile, index) => {
                        const previewUrl = getImagePreviewUrl(uploadedFile);
                        return (
                        <div 
                           key={`view-${index}`} 
                           className="relative group cursor-pointer"
                           onClick={() => previewUrl && handlePreviewImage(previewUrl)}
                        >
                           {previewUrl ? (
                           <>
                              <img
                              src={previewUrl}
                              alt={uploadedFile.file.name}
                              className="w-full h-24 object-cover rounded-lg border border-gray-200 hover:opacity-80 transition-opacity"
                              onError={(e) => {
                                 console.error('Image failed to load:', uploadedFile.file.name);
                                 const newUrl = URL.createObjectURL(uploadedFile.file);
                                 uploadedFile.preview = newUrl;
                                 (e.target as HTMLImageElement).src = newUrl;
                              }}
                              />
                              <div className="absolute inset-0 bg-black/0 group-hover:bg-black/20 transition-all duration-200 rounded-lg flex items-center justify-center pointer-events-none">
                              <Eye className="w-6 h-6 text-white opacity-0 group-hover:opacity-100 transition-opacity" />
                              </div>
                           </>
                           ) : (
                           <div className="w-full h-24 bg-gray-200 rounded-lg border border-gray-200 flex items-center justify-center">
                              <span className="text-xs text-gray-500">Image Error</span>
                           </div>
                           )}
                           <p className="text-xs text-gray-600 mt-1 truncate">{uploadedFile.file.name}</p>
                        </div>
                        );
                     })
                     }
                  </div>
                  </div>
               )}
               
               {/* Other Files List */}
               {uploadedFiles.filter(uploadedFile => !isImageFile(uploadedFile.file)).length > 0 && (
                  <div>
                  <h4 className="font-medium text-gray-900 mb-3">Other Files:</h4>
                  <div className="space-y-2">
                     {uploadedFiles
                     .filter(uploadedFile => !isImageFile(uploadedFile.file))
                     .map((uploadedFile, index) => (
                        <div key={index} className="flex items-center gap-3 p-2 bg-white rounded border">
                        <Upload className={`w-4 h-4 ${getFileTypeColor(uploadedFile.file)}`} />
                        <span className="text-sm font-medium text-gray-900 flex-1">{uploadedFile.file.name}</span>
                        <span className="text-xs text-gray-500">
                           ({(uploadedFile.file.size / 1024 / 1024).toFixed(2)} MB)
                        </span>
                        <Button variant="outline" size="sm">
                           <Download className="w-3 h-3" />
                        </Button>
                        </div>
                     ))
                     }
                  </div>
                  </div>
               )}
               </div>
            ) : (
               <span className="text-gray-500 italic">No {title.toLowerCase()} uploaded</span>
            )}
            </div>
         )}

         {/* Uploaded Files List in Edit Mode */}
         {isEditing && uploadedFiles.length > 0 && (
            <div className="mt-6">
            <h4 className="font-medium text-gray-900 mb-3">Uploaded Files:</h4>
            <div className="space-y-2">
               {uploadedFiles.map((uploadedFile, index) => (
               <div key={`edit-${index}-${uploadedFile.file.name}`} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg border">
                  <div className="flex items-center gap-3">
                  {isImageFile(uploadedFile.file) && uploadedFile.preview ? (
                     <img
                     src={uploadedFile.preview}
                     alt={uploadedFile.file.name}
                     className="w-10 h-10 object-cover rounded border"
                     onError={(e) => {
                        console.error('Thumbnail failed to load:', uploadedFile.file.name);
                        (e.target as HTMLImageElement).style.display = 'none';
                     }}
                     />
                  ) : (
                     <Upload className={`w-4 h-4 ${getFileTypeColor(uploadedFile.file)}`} />
                  )}
                  <div>
                     <span className="text-sm font-medium text-gray-900">{uploadedFile.file.name}</span>
                     <div className="text-xs text-gray-500">
                     ({(uploadedFile.file.size / 1024 / 1024).toFixed(2)} MB)
                     </div>
                  </div>
                  </div>
                  <div className="flex gap-2">
                  {isImageFile(uploadedFile.file) && uploadedFile.preview && (
                     <Button
                     variant="outline"
                     size="sm"
                     onClick={() => handlePreviewImage(uploadedFile.preview!)}
                     >
                     <Eye className="w-3 h-3" />
                     </Button>
                  )}
                  <Button
                     variant="outline"
                     size="sm"
                     onClick={() => removeFile(index)}
                     className="text-red-600 hover:text-red-700"
                  >
                     Remove
                  </Button>
                  </div>
               </div>
               ))}
            </div>
            </div>
         )}
         </div>

         {/* IMAGE PREVIEW MODAL */}
         {previewImage && (
         <div 
            className="fixed inset-0 w-screen h-screen bg-black bg-opacity-75 flex items-center justify-center z-50 p-4"
            style={{ left: 0, top: 0 }}
            onClick={closePreview}
         >
            <div 
            className="relative max-w-[90vw] max-h-[90vh] w-full h-full flex items-center justify-center"
            onClick={(e) => e.stopPropagation()}
            >
            <img
               src={previewImage}
               alt="Preview"
               className="max-w-full max-h-full object-contain rounded-lg shadow-2xl"
               style={{ 
               maxWidth: '90vw', 
               maxHeight: '90vh',
               width: 'auto',
               height: 'auto'
               }}
            />
            <Button
               variant="outline"
               size="sm"
               onClick={closePreview}
               className="absolute top-4 right-4 bg-white hover:bg-gray-100 shadow-lg z-10"
            >
               <X className="w-4 h-4" />
            </Button>
            
            <Button
               variant="outline"
               size="sm"
               onClick={() => {
               const link = document.createElement('a');
               link.href = previewImage;
               link.download = 'image';
               link.click();
               }}
               className="absolute top-4 right-16 bg-white hover:bg-gray-100 shadow-lg z-10"
            >
               <Download className="w-4 h-4" />
            </Button>
            </div>
         </div>
         )}
      </>
   );
}