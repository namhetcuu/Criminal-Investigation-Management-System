"use client";
import { Button } from "@/components/ui/button";

interface DeleteModalProps {
    onClose: () => void;
    onConfirm: () => void;
}

export default function DeleteModal({ onClose, onConfirm }: DeleteModalProps) {
    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/30">
            <div className="bg-white rounded-xl shadow-xl p-8 w-full max-w-md">
                <div className="flex items-start gap-4 mb-4">
                    <div className="w-2 h-10 rounded bg-red-400" />
                    <div>
                        <div className="text-xl font-bold text-red-600 mb-1">Delete Confirmation</div>
                        <p className="text-sm text-gray-700">
                            Are you sure you want to delete this item? This action cannot be undone.
                        </p>
                    </div>
                </div>
                <div className="flex justify-end gap-4 mt-6">
                    <Button variant="outline" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button className="bg-red-600 hover:bg-red-700 text-white" onClick={onConfirm}>
                        Delete
                    </Button>
                </div>
            </div>
        </div>
    );
}
