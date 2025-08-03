"use client";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { AlertTriangle } from "lucide-react";

/**
 * DELETE EVIDENCE MODAL COMPONENT
 * 
 * A confirmation modal for deleting evidence with a strong warning message.
 * Features a horizontal rectangular layout with warning styling.
 * 
 * USAGE:
 * <DeleteEvidenceModal
 *   isOpen={showDeleteModal}
 *   onClose={() => setShowDeleteModal(false)}
 *   onConfirm={() => handleDeleteEvidence()}
 *   evidenceName="Evidence PE-001" // Optional: specific evidence name
 * />
 * 
 * FEATURES:
 * - Horizontal rectangular layout
 * - Strong warning messaging with bold text
 * - Cancel and Delete action buttons
 * - Warning icon for visual emphasis
 * - Destructive styling for delete button
 */

interface DeleteEvidenceModalProps {
  isOpen: boolean;                    // Controls modal visibility
  onClose: () => void;               // Handler to close modal
  onConfirm: () => void;             // Handler to confirm deletion
  evidenceName?: string;             // Optional: specific evidence name to display
}

export function DeleteEvidenceModal({ 
  isOpen, 
  onClose, 
  onConfirm, 
  evidenceName 
}: DeleteEvidenceModalProps) {
  
  const handleConfirm = () => {
    onConfirm();
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-2xl w-full">
        {/* MODAL HEADER */}
        <DialogHeader className="text-center space-y-4">
          {/* WARNING ICON */}
          <div className="flex justify-center">
            <div className="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center">
              <AlertTriangle className="w-8 h-8 text-red-600" />
            </div>
          </div>
          
          {/* MAIN TITLE */}
          <DialogTitle className="text-2xl font-bold text-gray-900">
            Are you sure you want to Delete This Evidence?
            {evidenceName && (
              <div className="text-lg font-medium text-red-600 mt-2">
                {evidenceName}
              </div>
            )}
          </DialogTitle>
        </DialogHeader>

        {/* WARNING MESSAGE */}
        <DialogDescription className="text-center space-y-4 py-6">
          <div className="text-base text-gray-700 leading-relaxed">
            <span className="font-bold text-red-600">Warning:</span>{" "}
            <span className="font-bold">this action is irreversible</span>. 
            Deleting this piece of evidence will result in permanent loss of all 
            associated data, including its history, handling records, and any linked materials.
          </div>
          
          <div className="text-base text-gray-700">
            Please proceed{" "}
            <span className="font-bold">only if you are absolutely certain</span>{" "}
            this evidence should be removed from the system.
          </div>
        </DialogDescription>

        {/* ACTION BUTTONS */}
        <DialogFooter className="flex justify-center gap-4 pt-6">
          {/* CANCEL BUTTON */}
          <Button
            variant="outline"
            onClick={onClose}
            className="px-8 py-2 rounded-lg border-gray-300 hover:bg-gray-50"
          >
            Cancel
          </Button>
          
          {/* DELETE BUTTON */}
          <Button
            variant="destructive"
            onClick={handleConfirm}
            className="px-8 py-2 rounded-lg bg-red-600 hover:bg-red-700 text-white font-medium"
          >
            Delete Evidence
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}