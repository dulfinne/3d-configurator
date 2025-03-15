package com.dulfinne.configurator.util

object MinioConstants {
    const val PUBLIC_POLICY = """
        {
            "Version": "2012-10-17",
            "Statement": [
                {
                    "Effect": "Allow",
                    "Principal": {
                        "AWS": ["*"]
                    },
                    "Action": [
                        "s3:GetObject"
                    ],
                    "Resource": ["arn:aws:s3:::bucket-name/*"]
                }
            ]
        }
    """
}
