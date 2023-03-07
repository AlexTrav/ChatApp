package com.example.chatapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemContainerReceivedImageBinding;
import com.example.chatapp.databinding.ItemContainerReceivedMessageBinding;
import com.example.chatapp.databinding.ItemContainerSentImageBinding;
import com.example.chatapp.databinding.ItemContainerSentMessageBinding;
import com.example.chatapp.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> chatMessages;
    private Bitmap receiverProfileImage;
    private final String senderId;
    private String fileImage;
//    private Bitmap fileImage;
    private String fileSentImage, fileReceiverImage;
//    private Bitmap fileSentImage, fileReceiverImage;

//    private ChatMessage chatMessage;
    public int indexMessage = 0;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public void setReceiverProfileImage(Bitmap bitmap) {
        receiverProfileImage = bitmap;
//        fileImage = bitmap;
    }

    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiverProfileImage, String senderId/*, Bitmap fileImage*/) {
        this.chatMessages = chatMessages;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        fileImage = null;
        if (chatMessages.size() > 0) {
            fileImage = chatMessages.get(indexMessage).fileImage;
            indexMessage--;
        }
        if (viewType == VIEW_TYPE_SENT) {
            if (fileImage == null) {
                return new SentMessageViewHolder(
                        ItemContainerSentMessageBinding.inflate(
                                LayoutInflater.from(parent.getContext()),
                                parent,
                                false
                        )
                );
            } else {
                fileSentImage = fileImage;
                return new SentImageViewHolder(
                        ItemContainerSentImageBinding.inflate(
                                LayoutInflater.from(parent.getContext()),
                                parent,
                                false
                        )
                );
            }
        } else {
            if ( fileImage == null) {
                return new ReceivedMessageViewHolder(
                        ItemContainerReceivedMessageBinding.inflate(
                                LayoutInflater.from(parent.getContext()),
                                parent,
                                false
                        )
                );
            } else {
                fileReceiverImage = fileImage;
                return new ReceivedImageViewHolder(
                        ItemContainerReceivedImageBinding.inflate(
                                LayoutInflater.from(parent.getContext()),
                                parent,
                                false
                        )
                );

            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            if (fileImage == null) {
//            if (chatMessages.get(position).fileImage == null) {
                ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
            } else {
                ((SentImageViewHolder) holder).setData(chatMessages.get(position), getBitmapFromEncodedString(fileImage));
            }
        } else {
            if (fileImage == null) {
                ((ReceivedMessageViewHolder) holder).setData(chatMessages.get(position), receiverProfileImage);
            } else {
                ((ReceivedImageViewHolder) holder).setData(chatMessages.get(position), getBitmapFromEncodedString(fileImage));
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).senderId.equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding) {
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }
        void setData(ChatMessage chatMessage) {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerReceivedMessageBinding binding;

        ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding ItemContainerReceivedMessageBinding) {
            super(ItemContainerReceivedMessageBinding.getRoot());
            binding = ItemContainerReceivedMessageBinding;
        }
        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage) {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            if (receiverProfileImage != null) {
                binding.imageProfile.setImageBitmap(receiverProfileImage);
            }
        }
    }

    static class SentImageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerSentImageBinding binding;

        SentImageViewHolder(ItemContainerSentImageBinding itemContainerSentImageBinding) {
            super(itemContainerSentImageBinding.getRoot());
            binding = itemContainerSentImageBinding;
        }

        void setData(ChatMessage chatMessage, Bitmap sentFileImage) {
            binding.textDateTime.setText(chatMessage.dateTime);
            if (sentFileImage != null) {
                binding.sendImage.setImageBitmap(sentFileImage);
            }
        }
    }

    static class ReceivedImageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerReceivedImageBinding binding;

        ReceivedImageViewHolder(ItemContainerReceivedImageBinding itemContainerReceivedImageBinding) {
            super(itemContainerReceivedImageBinding.getRoot());
            binding = itemContainerReceivedImageBinding;
        }

        void setData(ChatMessage chatMessage, Bitmap receiverFileImage) {
            binding.textDateTime.setText(chatMessage.dateTime);
            if (receiverFileImage != null) {
                binding.sendImage.setImageBitmap(receiverFileImage);
            }
        }
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }

//    void setData(ChatMessage chatMessage) {
////            if (chatMessage.message == "") {
////                binding.textMessage.setText("");
////                if (chatMessage.sendFile != "") {
////                    byte[] bytes = Base64.decode(chatMessage.sendFile, Base64.DEFAULT);
////                    Bitmap sendImageT = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
////                    binding.sendImage.setImageBitmap(sendImageT);
////                    binding.sendImage.setVisibility(View.VISIBLE);
////                }
////            } else {
//        binding.textMessage.setText(chatMessage.message);
////            }
//        binding.textDateTime.setText(chatMessage.dateTime);
////            binding.sendImage.setImageBitmap(chatMessage.sendFile.#NAME);
////            binding.sendImage.setImageBitmap(res/drawable/images.jpg /*getChatImage(chatMessage.sendFile)*/);
////            binding.sendImage.setVisibility(View.VISIBLE);
//    }
}

