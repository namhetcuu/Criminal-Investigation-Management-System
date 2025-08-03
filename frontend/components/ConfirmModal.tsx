import { Button } from "@/components/ui/button";

export default function ConfirmModal({ onClose, onConfirm }: any) {
    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/30">
            <div className="bg-white rounded-xl shadow-xl p-8 w-full max-w-md">
                <div className="flex items-start gap-4 mb-4">
                    <div className="w-2 h-10 rounded bg-blue-300" />
                    <div>
                        <div className="text-xl font-bold mb-1">Declaration & Confirmation</div>
                        <ol className="text-gray-700 text-sm list-decimal pl-5">
                            <li>I hereby declare that all the information provided is true...</li>
                            <li>I accept full legal responsibility for any false or misleading info.</li>
                        </ol>
                    </div>
                </div>
                <div className="flex justify-end gap-4 mt-6">
                    <Button variant="outline" onClick={onClose}>Cancel</Button>
                    <Button className="bg-black text-white" onClick={onConfirm}>Yes</Button>
                </div>
            </div>
        </div>
    );
}
